package ru.v1as.tg.cat.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Chat;

@Getter
public class CatChatData extends ChatData {

    final Map<Integer, CatRequest> msgIdToCatRequests = new HashMap<>();
    final Map<Integer, CuriosCatRequest> msgIdToCuriosRequest = new HashMap<>();

    public CatChatData(Chat chat) {
        super(chat, chat.isUserChat());
    }

    public CatRequest getCatRequest(Integer messageId) {
        return msgIdToCatRequests.get(messageId);
    }

    public void registerCatRequest(CatRequest request, Integer messageId) {
        msgIdToCatRequests.put(messageId, request);
    }

    public List<CatRequest> getNotFinishedCatRequests() {
        return this.msgIdToCatRequests.values().stream()
                .filter(r -> !r.isFinished())
                .collect(Collectors.toList());
    }

    public Collection<CatRequest> getCatRequests() {
        return msgIdToCatRequests.values();
    }

    public void registerCuriosCatRequest(CuriosCatRequest request, Integer messageId) {
        msgIdToCuriosRequest.put(messageId, request);
    }

    public CuriosCatRequest getCuriosCatRequest(Integer msgId) {
        return msgIdToCuriosRequest.get(msgId);
    }
}