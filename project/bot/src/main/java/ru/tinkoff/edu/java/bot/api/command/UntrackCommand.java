package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
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
        if (text.equals(command())) {
            return "Введите ссылку";
        } else {
            //check link
            LinkResponse response = client.deleteLink(
                update.message().chat().id(), new RemoveLinkRequest(URI.create(text))
            ).block();
            return "Ссылка " + response.url() + " удалена из списка";
        }
    }

    @Override
    public boolean supports(Update update) {
        String text = update.message().text();
        Long chatId = update.message().chat().id();
        boolean answer;
        if (text.charAt(0) == '/') {
            answer = text.equals(command());
        } else {
            answer = isReply(update);
        }
        lastMessages.put(chatId, text);
        return answer;
    }

    private boolean isReply(Update update) {
        Long chatId = update.message().chat().id();
        return lastMessages.containsKey(chatId) && lastMessages.get(chatId).equals(command());
    }
}
