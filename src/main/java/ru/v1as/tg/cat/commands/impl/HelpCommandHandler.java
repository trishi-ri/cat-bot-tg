package ru.v1as.tg.cat.commands.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.v1as.tg.cat.commands.CommandHandler;
import ru.v1as.tg.cat.commands.TgCommandRequest;
import ru.v1as.tg.cat.model.TgChat;
import ru.v1as.tg.cat.model.TgUser;
import ru.v1as.tg.cat.tg.TgSender;

@Component
public class HelpCommandHandler extends AbstractCommand {

    private final List<CommandHandler> commands;
    private final TgSender sender;

    public HelpCommandHandler(List<CommandHandler> commands, TgSender sender) {
        super(cfg().commandName("help"));
        this.commands = commands;
        this.sender = sender;
    }

    @Override
    public void process(TgCommandRequest command, TgChat chat, TgUser user) {
        final String helpMessage =
                commands.stream()
                        .filter(c -> c.getCommandDescription() != null)
                        .map(
                                c ->
                                        String.format(
                                                "/%s - %s",
                                                c.getCommandName(), c.getCommandDescription()))
                        .collect(Collectors.joining("\n"));
        sender.message(chat, helpMessage);
    }
}
