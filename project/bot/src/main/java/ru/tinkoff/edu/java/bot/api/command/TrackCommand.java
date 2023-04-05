package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command{
    private final Map<Long, String> lastMessages = new HashMap<>();

    private final ScrapperClient client;
    private Command successor;

    @Autowired
    @Qualifier("untrackCommand")
    public void setSuccessor(Command successor){
        this.successor = successor;
    }
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
        String text = update.message().text();
        if (text.equals(command())){
            return "Введите ссылку";
        } else {
            //check link
            client.addLink(update.message().chat().id(), new AddLinkRequest(URI.create(text)));
            return "Ссылка добавлена в список";
        }
    }

    @Override
    public boolean supports(Update update){
        String text = update.message().text();
        Long chatId = update.message().chat().id();
        if (text.equals(command())){
            lastMessages.put(chatId, text);
            return true;
        } else if (!text.equals(successor.command())) {
            boolean answer = isReply(update);
            lastMessages.put(chatId, text);
            return answer;
        } else {
            lastMessages.put(chatId, text);
            return false;
        }
    }

    private boolean isReply(Update update){
        Long chatId = update.message().chat().id();
        return lastMessages.containsKey(chatId) && lastMessages.get(chatId).equals(command());
    }
}
