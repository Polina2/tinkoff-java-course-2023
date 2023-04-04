package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;

public class TrackCommand implements Command{
    @Override
    public Command successor() {
        return new UntrackCommand();
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
