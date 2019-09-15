package ru.v1as.tg.cat.tasks;

import static ru.v1as.tg.cat.tg.KeyboardUtils.deleteMsg;
import static ru.v1as.tg.cat.tg.KeyboardUtils.inlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.v1as.tg.cat.EmojiConst;
import ru.v1as.tg.cat.callbacks.curios.CuriosCatPolLCallback;
import ru.v1as.tg.cat.model.CatChatData;
import ru.v1as.tg.cat.model.CuriosCatRequest;
import ru.v1as.tg.cat.model.DbData;
import ru.v1as.tg.cat.tg.UnsafeAbsSender;

@Slf4j
@Setter
public class CuriosCatRequestScheduler {

    private final Random random = new Random();
    private final ScheduledExecutorService executorService;
    private DbData<CatChatData> data;
    private UnsafeAbsSender sender;
    private boolean firstTime = true;
    private int delayRange = 60;
    private int delayMin = 30;
    private double chance = 0.125;
    private int closeDelay = 3;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    public CuriosCatRequestScheduler(
            ScheduledExecutorService executorService,
            DbData<CatChatData> data,
            UnsafeAbsSender sender) {
        this.executorService = executorService;
        this.sender = sender;
        this.data = data;
        schedule();
    }

    void schedule() {
        int minutes = random.nextInt(delayRange) + delayMin;
        executorService.schedule(this::schedule, minutes, timeUnit);
        log.info("Next curios cat scheduled in {} {}", minutes, timeUnit);
        if (firstTime) {
            firstTime = false;
            return;
        }
        List<CuriosCatRequest> requests = new ArrayList<>();
        for (CatChatData chat : data.getChats()) {
            try {
                if (random.nextDouble() < chance) {
                    requests.add(sendMessage(chat));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("Curios cat was executed for {} chats", data.getChats().size());
        executorService.schedule(() -> closeOpenedRequests(requests), closeDelay, timeUnit);
    }

    void closeOpenedRequests(List<CuriosCatRequest> requests) {
        requests.stream()
                .filter(CuriosCatRequest::isOpen)
                .forEach(
                        r -> {
                            r.cancel();
                            sender.executeUnsafe(
                                    deleteMsg(r.getChat().getChatId(), r.getVoteMessage()));
                        });
    }

    CuriosCatRequest sendMessage(CatChatData chat) {
        CuriosCatRequest request = new CuriosCatRequest(chat);
        sender.executeAsyncUnsafe(
                new SendMessage()
                        .setChatId(chat.getChatId())
                        .setText(getCuriosCatMessage())
                        .setReplyMarkup(
                                inlineKeyboardMarkup(EmojiConst.CAT + " Кот!", "curiosCat")),
                new CuriosCatPolLCallback(chat, request));
        return request;
    }

    private String getCuriosCatMessage() {
        return "Любопытный кот гуляет рядом";
    }
}