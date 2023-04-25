package ru.tinkoff.edu.java.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.*;

@RequestMapping("/updates")
@RestController
public class BotController {

    @PostMapping
    public void sendUpdate(@RequestBody LinkUpdate linkUpdate){
    }
}
