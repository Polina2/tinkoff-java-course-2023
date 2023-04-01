package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.util.ArrayList;
import java.util.Random;

@RequestMapping("/links")
@RestController
public class ScrapperLinkController {
    private static final Random rand = new Random();

    @GetMapping
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        return new ListLinksResponse(new ArrayList<>(), rand.nextInt());
    }

    @PostMapping
    public void addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest link){
    }

    @DeleteMapping
    public void deleteLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest link){
    }
}
