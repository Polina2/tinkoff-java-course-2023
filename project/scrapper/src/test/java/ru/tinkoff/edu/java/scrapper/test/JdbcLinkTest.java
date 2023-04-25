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
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcLinkTest extends IntegrationEnvironment{

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TgChatRepository tgChatRepository;

    @Autowired
    private JdbcTemplate testJdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void addTest(){
        TgChat tgChat = new TgChat( 1L);
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")),
                tgChat.tgChatId()
        ).get(0);
        Link expectedLink = new Link("https://stackoverflow.com/questions/19896870", tgChat.tgChatId());

        linkRepository.add(expectedLink);

        List<Link> actualLinkList = testJdbcTemplate.query(
                "SELECT * FROM link WHERE url = ?",
                (rs, rn) -> new Link(
                        rs.getLong("id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update"),
                        rs.getTimestamp("last_check")
                ),
                expectedLink.url()
        );
        List<Long> actualSubscriptList = testJdbcTemplate.query(
                "SELECT link_id FROM subscription WHERE chat_id = ?",
                (rs, rn) -> rs.getLong("link_id"),
                actualTgChat.id()
        );
        Assertions.assertEquals(1, actualLinkList.size());
        Assertions.assertEquals(expectedLink.url(), actualLinkList.get(0).url());
        Assertions.assertEquals(1, actualSubscriptList.size());
        Assertions.assertEquals(actualLinkList.get(0).id(), actualSubscriptList.get(0));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat tgChat = new TgChat( 2L);
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")),
                tgChat.tgChatId()
        ).get(0);
        Link link = new Link("https://stackoverflow.com/questions/42215617", tgChat.tgChatId());
        linkRepository.add(link);

        linkRepository.remove(link);

        List<Link> actualLinkList = testJdbcTemplate.query(
                "SELECT * FROM link WHERE url = ?",
                (rs, rn) -> new Link(
                        rs.getLong("id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update"),
                        rs.getTimestamp("last_check")
                ),
                link.url()
        );
        List<Long> actualSubscriptList = testJdbcTemplate.query(
                "SELECT link_id FROM subscription WHERE chat_id = ?",
                (rs, rn) -> rs.getLong("link_id"),
                actualTgChat.id()
        );
        Assertions.assertEquals(1, actualLinkList.size());
        Assertions.assertEquals(Collections.EMPTY_LIST, actualSubscriptList);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest(){
        TgChat tgChat = new TgChat(3L);
        tgChatRepository.add(tgChat);

        Link link1 = new Link("https://stackoverflow.com/questions/42215617", tgChat.tgChatId());
        Link link2 = new Link("https://stackoverflow.com/questions/19896870", tgChat.tgChatId());
        linkRepository.add(link1);
        linkRepository.add(link2);

        List<Link> expectedList = List.of(link1, link2);

        List<Link> actualList = linkRepository.findAll();

        Assertions.assertEquals(
                expectedList.stream().map(Link::url).collect(Collectors.toList()),
                actualList.stream().map(Link::url).collect(Collectors.toList())
        );
    }
}
