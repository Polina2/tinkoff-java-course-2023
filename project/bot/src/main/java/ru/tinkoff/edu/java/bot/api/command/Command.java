package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;

public interface Command {

    String command();

    String description();

    String createReply(Update update);

    default boolean supports(Update update){
        return update.message().text().equals(command());
    }

    default BotCommand toBotCommand(){
        return new BotCommand(command(), description());
    }
}
