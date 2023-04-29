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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTgChatTest extends IntegrationEnvironment{
    @Autowired
    private JpaTgChatRepository tgChatRepository;
    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest(){
        TgChat expectedTgChat = new TgChat();
        expectedTgChat.setTgChatId(1L);

        tgChatRepository.save(expectedTgChat);

        TgChat actualTgChat = tgChatRepository.findByTgChatId(expectedTgChat.getTgChatId());
        Assertions.assertEquals(expectedTgChat.getTgChatId(), actualTgChat.getTgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat expectedTgChat = new TgChat();
        expectedTgChat.setTgChatId(2L);
        tgChatRepository.save(expectedTgChat);

        tgChatRepository.deleteByTgChatId(expectedTgChat.getTgChatId());

        TgChat actualTgChat = tgChatRepository.findByTgChatId(expectedTgChat.getTgChatId());
        Assertions.assertNull(actualTgChat);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest(){
        TgChat tgChat1 = new TgChat();
        tgChat1.setTgChatId(3L);
        TgChat tgChat2 = new TgChat();
        tgChat2.setTgChatId(4L);
        List<TgChat> expectedList = List.of(tgChat1, tgChat2);
        tgChatRepository.save(tgChat1);
        tgChatRepository.save(tgChat2);

        List<TgChat> actualList = tgChatRepository.findAll();

        Assertions.assertEquals(expectedList.size(), actualList.size());
        Assertions.assertEquals(
                expectedList.stream().map(TgChat::getTgChatId).toList(),
                actualList.stream().map(TgChat::getTgChatId).toList()
        );
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest(){
        Link link = new Link();
        link.setUrl("sdfgh");

        TgChat tgChat1 = new TgChat();
        tgChat1.setTgChatId(123L);
        tgChat1.setLinks(List.of(link));

        TgChat tgChat2 = new TgChat();
        tgChat2.setTgChatId(345L);
        tgChat2.setLinks(List.of(link));

        linkRepository.save(link);
        tgChatRepository.save(tgChat1);
        tgChatRepository.save(tgChat2);
        List<TgChat> expectedList = List.of(tgChat1, tgChat2);

        List<TgChat> actualList = tgChatRepository.findByLinks_Url(link.getUrl());

        Assertions.assertNotNull(actualList);
        Assertions.assertEquals(
                expectedList.stream().map(TgChat::getTgChatId).toList(),
                actualList.stream().map(TgChat::getTgChatId).toList()
        );
    }
}
