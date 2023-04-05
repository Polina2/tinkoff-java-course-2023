package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command{

    private final ScrapperClient client;
    private Command successor;

    @Autowired
    @Qualifier("untrackCommand")
    @Override
    public Command successor() {
        return this.successor;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public String createReply(Update update) {
        String answer = "Ссылка добавлена в список";
        return answer;
    }
}
