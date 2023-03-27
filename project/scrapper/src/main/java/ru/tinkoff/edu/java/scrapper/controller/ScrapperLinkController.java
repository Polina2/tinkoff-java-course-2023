package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.Response;

import java.util.ArrayList;
import java.util.Random;

@RequestMapping("/links")
@RestController
public class ScrapperLinkController {
    private static final Random rand = new Random();

    @GetMapping
    public ListLinksResponse getLinks(@RequestHeader Long tgChatId) {
        return new ListLinksResponse(new ArrayList<>(), rand.nextInt());
    }

    @PostMapping
    public Response addLink(@RequestHeader Long tgChatId, @RequestBody AddLinkRequest link){
        return new Response("Ссылка успешно добавлена");
    }

    @DeleteMapping
    public Response deleteLink(@RequestHeader Long tgChatId, @RequestBody RemoveLinkRequest link){
        return new Response("Ссылка успешно убрана");
    }
}
