package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final ScrapperClient client;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public String createReply(Update update) {
        client.addChat(update.message().chat().id());
        return "Привет! Давайте начнем. Для получения списка доступных команд введите /help";
    }
}
