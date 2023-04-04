package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;

public class StartCommand implements Command {

    @Override
    public Command successor() {
        return new TrackCommand();
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
        String text = "Привет! Давайте начнем. Для получения списка доступных команд введите /help";
        return text;
    }
}
