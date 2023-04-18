package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Override
    public void add(long tgChatId, URI url) {
        Link link = new Link(url.toString(), tgChatId);
        linkRepository.add(link);
    }

    @Override
    public void remove(long tgChatId, URI url) {
        Link link = new Link(url.toString(), tgChatId);
        linkRepository.remove(link);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.findByTgChatId(tgChatId);
    }
}
