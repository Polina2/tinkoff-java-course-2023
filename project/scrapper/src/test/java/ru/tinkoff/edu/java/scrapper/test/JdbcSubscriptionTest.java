package ru.tinkoff.edu.java.scrapper.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Subscription;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcSubscriptionTest extends IntegrationEnvironment{
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TgChatRepository tgChatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JdbcTemplate testJdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void addTest(){
        TgChat tgChat = new TgChat(123L);
        Link link = new Link("a");
        tgChatRepository.add(tgChat);
        linkRepository.add(link);
        Subscription expected = new Subscription(tgChatRepository.getId(tgChat), linkRepository.getId(link));

        subscriptionRepository.add(expected);

        List<Subscription> actualList = testJdbcTemplate.query(
                "SELECT * FROM subscription WHERE chat_id = ? AND link_id = ?",
                (rs, rn) -> new Subscription(rs.getLong("chat_id"), rs.getLong("link_id")),
                expected.chatId(), expected.linkId()
        );
        Assertions.assertEquals(1, actualList.size());
        Assertions.assertEquals(expected, actualList.get(0));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat tgChat = new TgChat(12L);
        Link link = new Link("b");
        tgChatRepository.add(tgChat);
        linkRepository.add(link);
        Subscription expected = new Subscription(tgChatRepository.getId(tgChat), linkRepository.getId(link));
        subscriptionRepository.add(expected);

        subscriptionRepository.remove(expected);

        List<Subscription> actualList = testJdbcTemplate.query(
                "SELECT * FROM subscription WHERE chat_id = ? AND link_id = ?",
                (rs, rn) -> new Subscription(rs.getLong("chat_id"), rs.getLong("link_id")),
                expected.chatId(), expected.linkId()
        );

        Assertions.assertEquals(Collections.EMPTY_LIST, actualList);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest(){
        TgChat tgChat = new TgChat(23L);
        Link link1 = new Link("c");
        Link link2 = new Link("asd");
        tgChatRepository.add(tgChat);
        linkRepository.add(link1);
        linkRepository.add(link2);

        Subscription subscription1 = new Subscription(tgChatRepository.getId(tgChat), linkRepository.getId(link1));
        Subscription subscription2 = new Subscription(tgChatRepository.getId(tgChat), linkRepository.getId(link2));
        List<Subscription> expectedList = List.of(subscription1, subscription2);
        subscriptionRepository.add(subscription1);
        subscriptionRepository.add(subscription2);

        List<Subscription> actualList = subscriptionRepository.findAll();

        Assertions.assertEquals(expectedList, actualList);
    }
}
