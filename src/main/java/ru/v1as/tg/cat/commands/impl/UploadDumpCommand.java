package ru.v1as.tg.cat.commands.impl;

import static ru.v1as.tg.cat.service.Const.onlyForAdminCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.v1as.tg.cat.commands.CommandHandler;
import ru.v1as.tg.cat.commands.TgCommandRequest;
import ru.v1as.tg.cat.messages.DumpDocumentMessageHandler;
import ru.v1as.tg.cat.model.TgChat;
import ru.v1as.tg.cat.model.TgUser;
import ru.v1as.tg.cat.tg.TgSender;

@Component
@RequiredArgsConstructor
public class UploadDumpCommand implements CommandHandler {

    private static final String NAME = "upload_dump";
    private final DumpDocumentMessageHandler dumpDocumentMessageHandler;
    private final TgSender sender;

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public void handle(TgCommandRequest command, TgChat chat, TgUser user) {
        onlyForAdminCheck(user);
        dumpDocumentMessageHandler.addRequest(
                command.getMessage(),
                () -> sender.execute(new SendMessage(chat.getId(), "Не дождался дамп файла.")));
        sender.execute(new SendMessage(chat.getId(), "Теперь пришлите dump.sql файл"));
    }
}