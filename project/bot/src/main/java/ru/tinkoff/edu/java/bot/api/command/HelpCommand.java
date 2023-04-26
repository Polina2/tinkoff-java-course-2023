package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command{

    private final List<Command> commands;

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
        StringBuilder text = new StringBuilder();
        text.append(this.command()).append(" -- ").append(this.description()).append('\n');
        for (Command command : commands){
            text.append(command.command()).append(" -- ").append(command.description()).append('\n');
        }
        return text.toString();
    }
}
