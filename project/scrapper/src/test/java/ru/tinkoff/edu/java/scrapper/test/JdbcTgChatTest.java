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
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTgChatTest extends IntegrationEnvironment{

    @Autowired
    private TgChatRepository tgChatRepository;

    @Autowired
    private JdbcTemplate testJdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        TgChat tgChat1 = new TgChat( "user1");
        TgChat tgChat2 = new TgChat( "user2");
        List<TgChat> expectedList = List.of(tgChat1, tgChat2);
        tgChatRepository.add(tgChat1);
        tgChatRepository.add(tgChat2);

        List<TgChat> actualList = tgChatRepository.findAll();

        Assertions.assertEquals(
                expectedList.stream().map(TgChat::name).collect(Collectors.toList()),
                actualList.stream().map(TgChat::name).collect(Collectors.toList())
                );
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        TgChat expectedTgChat = new TgChat("user1");

        tgChatRepository.add(expectedTgChat);
        List<TgChat> actualList = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                expectedTgChat.name()
        );

        Assertions.assertEquals(1, actualList.size());
        Assertions.assertEquals(expectedTgChat.name(), actualList.get(0).name());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat tgChat = new TgChat( "user2");
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                tgChat.name()
        ).get(0);

        tgChatRepository.remove(actualTgChat);

        List<TgChat> actualList = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                tgChat.name()
        );
        Assertions.assertEquals(Collections.EMPTY_LIST, actualList);
    }
}
