package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

public interface Command {

    ScrapperClient client = new ScrapperClient();

    Command successor();

    String command();

    String description();

    default String handle(Update update){
        if (this.supports(update)){
            return createReply(update);
        } else {
            if (this.successor() != null)
                return this.successor().handle(update);
            else
                return "Я вас не понимаю. Для получения списка доступных команд введите /help";
        }
    }

    String createReply(Update update);

    default boolean supports(Update update){
        return update.message().text().equals(command());
    }

    default BotCommand toBotCommand(){
        return new BotCommand(command(), description());
    }
}
