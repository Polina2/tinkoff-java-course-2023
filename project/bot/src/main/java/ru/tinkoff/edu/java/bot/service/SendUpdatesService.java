package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.api.BotImplementation;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@Service
@RequiredArgsConstructor
public class SendUpdatesService {
    private final BotImplementation bot;
    public void sendUpdate(LinkUpdate linkUpdate){
        for (Long id : linkUpdate.tgChatIds()){
            bot.sendUpdate(new SendMessage(id,
                    linkUpdate.url().toString()+": "+linkUpdate.description()));
        }
    }
}
