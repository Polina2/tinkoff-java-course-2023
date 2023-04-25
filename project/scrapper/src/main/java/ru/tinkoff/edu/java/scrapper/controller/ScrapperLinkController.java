package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;

@RequestMapping("/links")
@RestController
@RequiredArgsConstructor
public class ScrapperLinkController {
    private final LinkService linkService;

    @GetMapping
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        List<Link> links = (List<Link>) linkService.listAll(tgChatId);
        return new ListLinksResponse(
                links.stream().map(link -> new LinkResponse(link.id(), URI.create(link.url()))).toList(),
                links.size()
        );
    }

    @PostMapping
    public void addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest link){
        linkService.add(tgChatId, link.link());
    }

    @DeleteMapping
    public void deleteLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest link){
        linkService.remove(tgChatId, link.link());
    }
}
