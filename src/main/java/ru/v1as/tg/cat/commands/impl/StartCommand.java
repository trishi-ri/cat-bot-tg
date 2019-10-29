package ru.v1as.tg.cat.commands.impl;

import java.util.function.Consumer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.v1as.tg.cat.commands.ArgumentCallbackCommand;
import ru.v1as.tg.cat.tg.UnsafeAbsSender;

@Component
public class StartCommand extends ArgumentCallbackCommand {

    public static final String START_COMMAND_NAME = "start";

    public StartCommand(UnsafeAbsSender sender) {
        super(getDefaultBehaviour(sender));
    }

    private static Consumer<CallbackCommandContext> getDefaultBehaviour(UnsafeAbsSender sender) {
        return ctx -> sender.executeUnsafe(new SendMessage(ctx.getChat().getId(), "Приветствую!"));
    }

    @Override
    public String getCommandName() {
        return START_COMMAND_NAME;
    }
}
