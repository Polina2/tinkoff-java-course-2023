package ru.tinkoff.edu.java.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.SendUpdatesService;

@RequestMapping("/updates")
@RestController
@RequiredArgsConstructor
public class BotController {

    private final SendUpdatesService sendUpdatesService;

    @PostMapping
    public void sendUpdate(@RequestBody LinkUpdate linkUpdate){
        sendUpdatesService.sendUpdate(linkUpdate);
    }
}
