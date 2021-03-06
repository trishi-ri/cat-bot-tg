package ru.v1as.tg.cat.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.v1as.tg.cat.commands.CommandHandler;
import ru.v1as.tg.cat.commands.TgCommandRequest;
import ru.v1as.tg.cat.jpa.dao.ChatDetailsDao;
import ru.v1as.tg.cat.jpa.entities.chat.ChatDetailsEntity;
import ru.v1as.tg.cat.model.TgChat;
import ru.v1as.tg.cat.model.TgUser;
import ru.v1as.tg.cat.tg.TgSender;

@Component
@RequiredArgsConstructor
public class DisablePollsCommand implements CommandHandler {

    private final ChatDetailsDao chatDetailsDao;
    private final TgSender sender;

    @Override
    public String getCommandName() {
        return "disable_polls";
    }

    @Override
    public String getCommandDescription() {
        return "Выключить опросы 'Это кот?'.";
    }

    @Override
    public void handle(TgCommandRequest command, TgChat chat, TgUser user) {
        if (chat.isUserChat()) {
            sender.message(chat, "Эту комманду можно выполнять только в групповом чате.");
            return;
        }
        final ChatDetailsEntity details = chatDetailsDao.findByChatId(chat.getId());
        if (!details.isCatPollEnabled()) {
            sender.message(chat, "Создание опросов уже выключено");
        } else {
            details.setCatPollEnabled(false);
            chatDetailsDao.save(details);
            sender.message(chat, "Создание опросов теперь выключено");
        }
    }
}
