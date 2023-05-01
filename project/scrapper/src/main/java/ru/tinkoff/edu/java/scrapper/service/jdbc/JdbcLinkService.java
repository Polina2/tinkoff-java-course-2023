package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Subscription;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TgChatRepository tgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Link link = new Link(url.toString());
        linkRepository.add(link);
        Long linkId = linkRepository.getId(link);
        Long chatId = tgChatRepository.getId(new TgChat(tgChatId));
        subscriptionRepository.add(new Subscription(chatId, linkId));
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = new Link(url.toString());
        Long linkId = linkRepository.getId(link);
        Long chatId = tgChatRepository.getId(new TgChat(tgChatId));
        subscriptionRepository.remove(new Subscription(chatId, linkId));

        if (subscriptionRepository.findAll().stream().filter(s -> s.linkId().equals(linkId)).toList().size() == 0)
            linkRepository.remove(link);

        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.findByTgChatId(tgChatId);
    }

    @Override
    public Collection<Link> listNotChecked() {
        return linkRepository.findNotChecked();
    }

    @Override
    public void updateLink(Link link, Timestamp lastUpdate, String updateInfo) {
        linkRepository.updateLink(link, lastUpdate);
    }

}
