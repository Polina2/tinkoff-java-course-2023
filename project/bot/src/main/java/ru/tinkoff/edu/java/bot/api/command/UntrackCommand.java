package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command{
    private final Map<Long, String> lastMessages = new HashMap<>();

    private final ScrapperClient client;

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
        String text = update.message().text();
        if (text.equals(command())){
            return "Введите ссылку";
        } else {
            //check link
            client.deleteLink(update.message().chat().id(), new RemoveLinkRequest(URI.create(text)));
            return "Ссылка удалена из списка";
        }
    }

    @Override
    public boolean supports(Update update){
        String text = update.message().text();
        Long chatId = update.message().chat().id();
        lastMessages.put(chatId, text);
        if (text.charAt(0) == '/'){
            return text.equals(command());
        } else {
            return isReply(update);
        }
    }

    private boolean isReply(Update update){
        Long chatId = update.message().chat().id();
        return lastMessages.containsKey(chatId) && lastMessages.get(chatId).equals(command());
    }
}