package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.*;

import java.util.ArrayList;
import java.util.Random;

@RequestMapping("/links")
@RestController
public class ScrapperLinkController {
    private static final Random rand = new Random();

    @GetMapping
    public ListLinksResponse getLinks(@RequestParam Long tgChatId) {
        return new ListLinksResponse(new ArrayList<>(), rand.nextInt());
    }

    @PostMapping
    public Response addLink(@RequestParam Long tgChatId, @RequestBody AddLinkRequest link){
        return new Response("Ссылка успешно добавлена");
    }

    @DeleteMapping
    public Response deleteLink(@RequestParam Long tgChatId, @RequestBody RemoveLinkRequest link){
        return new Response("Ссылка успешно убрана");
    }
}
