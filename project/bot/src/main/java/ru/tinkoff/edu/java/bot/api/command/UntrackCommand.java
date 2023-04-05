package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command{

    private final ScrapperClient client;

    @Override
    public Command successor() {
        return null;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public String createReply(Update update) {
        String answer = "Ссылка удалена из списка";
        return answer;
    }
}
