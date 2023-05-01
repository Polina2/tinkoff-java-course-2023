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
        TgChat tgChat1 = new TgChat( 12L);
        TgChat tgChat2 = new TgChat( 234L);
        List<TgChat> expectedList = List.of(tgChat1, tgChat2);
        tgChatRepository.add(tgChat1);
        tgChatRepository.add(tgChat2);

        List<TgChat> actualList = tgChatRepository.findAll();

        Assertions.assertEquals(
                expectedList.stream().map(TgChat::tgChatId).collect(Collectors.toList()),
                actualList.stream().map(TgChat::tgChatId).collect(Collectors.toList())
                );
    }

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        TgChat expectedTgChat = new TgChat(1L);

        tgChatRepository.add(expectedTgChat);

        List<TgChat> actualList = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")),
                expectedTgChat.tgChatId()
        );
        Assertions.assertEquals(1, actualList.size());
        Assertions.assertEquals(expectedTgChat.tgChatId(), actualList.get(0).tgChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat tgChat = new TgChat( 2L);
        tgChatRepository.add(tgChat);

        tgChatRepository.remove(tgChat);

        List<TgChat> actualList = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")),
                tgChat.tgChatId()
        );
        Assertions.assertEquals(Collections.EMPTY_LIST, actualList);
    }
}
