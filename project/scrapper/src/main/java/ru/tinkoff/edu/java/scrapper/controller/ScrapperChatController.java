package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequestMapping("/tg-chat")
@RestController
@RequiredArgsConstructor
public class ScrapperChatController {
    private final TgChatService tgChatService;

    @PostMapping("/{id}")
    public void addChat(@PathVariable Long id) {
        tgChatService.register(id);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable Long id) {
        tgChatService.unregister(id);
    }

}
