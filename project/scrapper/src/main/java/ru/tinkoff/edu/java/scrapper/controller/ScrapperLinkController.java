package ru.tinkoff.edu.java.scrapper.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

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
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest link) {
        Link linkDto = linkService.add(tgChatId, link.link());
        return new LinkResponse(linkDto.id(), URI.create(linkDto.url()));
    }

    @DeleteMapping
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest link) {
        Link linkDto = linkService.remove(tgChatId, link.link());
        return new LinkResponse(linkDto.id(), URI.create(linkDto.url()));
    }
}
