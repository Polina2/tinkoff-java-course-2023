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
        TgChat tgChat = new TgChat( "user");
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                tgChat.name()
        ).get(0);
        Link expectedLink = new Link("https://stackoverflow.com/questions/19896870", actualTgChat.id());

        linkRepository.add(expectedLink);

        List<Link> actualLinkList = testJdbcTemplate.query(
                "SELECT * FROM link WHERE url = ?",
                (rs, rn) -> new Link(
                        rs.getLong("link_id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update")
                ),
                expectedLink.url()
        );
        List<Long> actualSubscriptList = testJdbcTemplate.query(
                "SELECT * FROM subscription WHERE chat_id = ?",
                (rs, rn) -> rs.getLong("link_id"),
                actualTgChat.id()
        );
        Assertions.assertEquals(1, actualLinkList.size());
        Assertions.assertEquals(expectedLink.url(), actualLinkList.get(0).url());
        Assertions.assertEquals(1, actualSubscriptList.size());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest(){
        TgChat tgChat = new TgChat( "user");
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                tgChat.name()
        ).get(0);
        Link link = new Link("https://stackoverflow.com/questions/42215617", actualTgChat.id());
        linkRepository.add(link);

        linkRepository.remove(link);

        List<Link> actualLinkList = testJdbcTemplate.query(
                "SELECT * FROM link WHERE url = ?",
                (rs, rn) -> new Link(
                        rs.getLong("link_id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update")
                ),
                link.url()
        );
        List<Long> actualSubscriptList = testJdbcTemplate.query(
                "SELECT * FROM subscription WHERE chat_id = ?",
                (rs, rn) -> rs.getLong("link_id"),
                actualTgChat.id()
        );
        Assertions.assertEquals(Collections.EMPTY_LIST, actualLinkList);
        Assertions.assertEquals(Collections.EMPTY_LIST, actualSubscriptList);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest(){
        TgChat tgChat = new TgChat("user");
        tgChatRepository.add(tgChat);
        TgChat actualTgChat = testJdbcTemplate.query(
                "SELECT * FROM tg_chat WHERE name = ?",
                (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")),
                tgChat.name()
        ).get(0);

        Link link1 = new Link("https://stackoverflow.com/questions/42215617", actualTgChat.id());
        Link link2 = new Link("https://stackoverflow.com/questions/19896870", actualTgChat.id());
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
