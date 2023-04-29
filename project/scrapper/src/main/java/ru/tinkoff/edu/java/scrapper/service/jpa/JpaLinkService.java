package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;
    private final JpaTgChatRepository tgChatRepository;

    @Transactional
    @Override
    public void add(long tgChatId, URI url) {
        ru.tinkoff.edu.java.scrapper.entity.Link link = new ru.tinkoff.edu.java.scrapper.entity.Link();
        link.setUrl(url.toString());
        linkRepository.save(link);
        TgChat tgChat = tgChatRepository.findByTgChatId(tgChatId);
        link = linkRepository.findByUrl(link.getUrl());
        tgChat.getLinks().add(link);
        tgChatRepository.save(tgChat);
    }

    @Transactional
    @Override
    public void remove(long tgChatId, URI url) {
        TgChat tgChat = tgChatRepository.findByTgChatId(tgChatId);
        tgChat.getLinks().remove(linkRepository.findByUrl(url.toString()));
        tgChatRepository.save(tgChat);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.findByTgChats_TgChatId(tgChatId).stream()
                .map(link -> new Link(
                        link.getId(), link.getUrl(), link.getLastUpdate(),
                        link.getLastCheck(), link.getUpdateInfo())
                ).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Link> listNotChecked() {
        return linkRepository.findByLastCheckLessThan(Timestamp.valueOf(LocalDateTime.now().minusHours(2)))
                .stream().map(link -> new Link(
                        link.getId(), link.getUrl(), link.getLastUpdate(),
                        link.getLastCheck(), link.getUpdateInfo())
                ).toList();
    }

    @Transactional
    @Override
    public void updateLink(Link link, Timestamp lastUpdate, String updateInfo) {
        ru.tinkoff.edu.java.scrapper.entity.Link linkEntity = linkRepository.findByUrl(link.url());
        linkEntity.setLastUpdate(lastUpdate);
        linkEntity.setUpdateInfo(updateInfo);
        linkRepository.save(linkEntity);
    }
}
