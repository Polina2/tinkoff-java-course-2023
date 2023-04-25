package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.api.BotImplementation;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

@RequestMapping("/updates")
@RestController
@RequiredArgsConstructor
public class BotController {

    private final BotImplementation bot;

    @PostMapping
    public void sendUpdate(@RequestBody LinkUpdate linkUpdate){
        for (Long id : linkUpdate.tgChatIds()){
            bot.sendUpdate(new SendMessage(id,
                    linkUpdate.url().toString()+": "+linkUpdate.description()));
        }
    }
}
