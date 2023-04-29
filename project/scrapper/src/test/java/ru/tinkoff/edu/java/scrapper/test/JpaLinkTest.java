package ru.tinkoff.edu.java.scrapper.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.JpaTgChatRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaLinkTest extends IntegrationEnvironment{

    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    @Autowired
    private JpaTgChatRepository jpaTgChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest(){
        Link expectedLink = new Link();
        expectedLink.setUrl("abc");

        jpaLinkRepository.save(expectedLink);

        Link actualLink = jpaLinkRepository.findByUrl(expectedLink.getUrl());
        Assertions.assertEquals(expectedLink.getUrl(), actualLink.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        Link expectedLink = new Link();
        expectedLink.setUrl("qwe");
        jpaLinkRepository.save(expectedLink);

        jpaLinkRepository.deleteByUrl(expectedLink.getUrl());

        Link actualLink = jpaLinkRepository.findByUrl(expectedLink.getUrl());
        Assertions.assertNull(actualLink);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest(){
        Link link1 = new Link();
        link1.setUrl("qwerty");
        Link link2 = new Link();
        link2.setUrl("asdfgh");
        jpaLinkRepository.save(link1);
        jpaLinkRepository.save(link2);
        List<Link> expectedList = List.of(link1, link2);

        List<Link> actualList = jpaLinkRepository.findAll();

        Assertions.assertEquals(expectedList.size(), actualList.size());
        Assertions.assertEquals(
                expectedList.stream().map(Link::getUrl).toList(),
                actualList.stream().map(Link::getUrl).toList()
        );
    }

    @Test
    @Transactional
    @Rollback
    public void findByTgChatIdTest(){
        TgChat tgChat = new TgChat();
        tgChat.setTgChatId(34L);
        Link link1 = new Link();
        link1.setUrl("qqq");
        Link link2 = new Link();
        link2.setUrl("aaa");

        List<Link> expectedList = List.of(link1, link2);
        tgChat.setLinks(expectedList);

        jpaLinkRepository.save(link1);
        jpaLinkRepository.save(link2);
        jpaTgChatRepository.save(tgChat);

        List<Link> actualList = jpaLinkRepository.findByTgChats_TgChatId(tgChat.getTgChatId());

        Assertions.assertEquals(
                expectedList.stream().map(Link::getUrl).toList(),
                actualList.stream().map(Link::getUrl).toList()
        );
    }

    @Test
    @Transactional
    @Rollback
    public void removeSubscriptionTest(){
        Link expectedLink = new Link();
        expectedLink.setUrl("qwe");
        jpaLinkRepository.save(expectedLink);
        TgChat tgChat = new TgChat();
        tgChat.setTgChatId(2L);
        jpaTgChatRepository.save(tgChat);

        tgChat = jpaTgChatRepository.findByTgChatId(tgChat.getTgChatId());
        tgChat.getLinks().remove(expectedLink);
        jpaTgChatRepository.save(tgChat);

        tgChat = jpaTgChatRepository.findByTgChatId(tgChat.getTgChatId());
        Assertions.assertEquals(Collections.EMPTY_LIST, tgChat.getLinks());
    }

    @Test
    @Transactional
    @Rollback
    public void findNotCheckedTest(){
        Link link = new Link();
        link.setUrl("gf");
        jpaLinkRepository.save(link);

        List<Link> actualList = jpaLinkRepository.findByLastCheckLessThan(
                Timestamp.valueOf(LocalDateTime.now().minusHours(2))
        );

        Assertions.assertEquals(Collections.EMPTY_LIST, actualList);
    }
}
