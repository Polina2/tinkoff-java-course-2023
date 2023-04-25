package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequestMapping("/tg-chat")
@RestController
@RequiredArgsConstructor
public class ScrapperChatController {
    private final TgChatService tgChatService;

    @PostMapping("/{id}")
    public void addChat(@PathVariable Long id){
        tgChatService.register(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable Long id){
        tgChatService.unregister(id);
    }

}
