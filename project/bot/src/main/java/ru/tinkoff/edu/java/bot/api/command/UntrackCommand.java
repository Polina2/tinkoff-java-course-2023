package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;

public class UntrackCommand implements Command{
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
