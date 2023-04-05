package ru.tinkoff.edu.java.bot.api;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.api.command.HelpCommand;
@Component
@RequiredArgsConstructor
public class UserMessageProcessor {
    private final HelpCommand helpCommand;

    public SendMessage process(Update update){
        String text = helpCommand.handle(update);
        return new SendMessage(update.message().chat().id(), text);
    }
}
