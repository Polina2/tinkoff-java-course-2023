package ru.tinkoff.edu.java.bot.api;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.api.command.HelpCommand;

public class UserMessageProcessor {

    public SendMessage process(Update update){
        HelpCommand helpCommand = new HelpCommand();
        String text = helpCommand.handle(update);
        return new SendMessage(update.message().chat().id(), text);
    }
}
