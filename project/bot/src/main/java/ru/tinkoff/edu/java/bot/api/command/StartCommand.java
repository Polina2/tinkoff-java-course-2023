package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final ScrapperClient client;
    private Command successor;

    @Autowired
    @Qualifier("trackCommand")
    @Override
    public Command successor() {
        return this.successor;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Начать";
    }

    @Override
    public String createReply(Update update) {
        client.addChat(update.message().chat().id());
        String text = "Привет! Давайте начнем. Для получения списка доступных команд введите /help";
        return text;
    }
}
