package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command{
    private Command successor;

    @Autowired
    @Qualifier("listCommand")
    public void setSuccessor(Command command){
        this.successor = command;
    }

    @Override
    public Command successor() {
        return this.successor;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Список команд";
    }

    @Override
    public String createReply(Update update) {
        String text = """
                /start -- зарегистрировать пользователя
                /help -- вывести окно с командами
                /track -- начать отслеживание ссылки
                /untrack -- прекратить отслеживание ссылки
                /list -- показать список отслеживаемых ссылок""";
        return text;
    }
}
