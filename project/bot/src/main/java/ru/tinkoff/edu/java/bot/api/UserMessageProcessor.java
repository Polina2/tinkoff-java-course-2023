package ru.tinkoff.edu.java.bot.api;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.api.command.Command;

@Component
@RequiredArgsConstructor
public class UserMessageProcessor {

    private final List<Command> commands;

    public SendMessage process(Update update) {
        return new SendMessage(update.message().chat().id(), createReply(update));
    }

    public String createReply(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                return command.createReply(update);
            }
        }
        return "Я вас не понимаю. Для получения списка доступных команд введите /help";
    }
}
