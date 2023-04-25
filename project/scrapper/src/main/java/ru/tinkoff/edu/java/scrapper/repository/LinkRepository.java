package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LinkRepository implements IRepository<Link> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(Link object) {
        String sqlLink = "INSERT INTO link (url) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sqlLink, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, object.url());
            return ps;
        }, keyHolder);

        Long tgChatId = jdbcTemplate.query(
                "SELECT id FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> rs.getLong("id"),
                object.tgChatId()
        ).get(0);

        String sqlSubscript = "INSERT INTO subscription VALUES (?, ?)";
        jdbcTemplate.update(sqlSubscript, tgChatId, keyHolder.getKeys().get("id"));
    }

    @Override
    public void remove(Link object) {
        Long tgChatId = jdbcTemplate.query(
                "SELECT id FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> rs.getLong("id"),
                object.tgChatId()
        ).get(0);

        Long linkId = jdbcTemplate.query(
                "SELECT id FROM link WHERE url = ?",
                (rs, rn) -> rs.getLong("id"),
                object.url()
        ).get(0);

        String sqlSubscript = "DELETE FROM subscription WHERE chat_id = ? AND link_id = ?";
        jdbcTemplate.update(sqlSubscript, tgChatId, linkId);
    }

    @Override
    public List<Link> findAll() {
        String sql = "SELECT * FROM link";

        List<Link> list = jdbcTemplate.query(
                sql, (rs, rn) -> new Link(
                        rs.getLong("id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update")
                ));
        return list;
    }

    public List<Link> findByTgChatId(Long tgChatId){
        String sql = """
                SELECT *
                FROM link
                WHERE id = (
                SELECT link_id
                FROM subscription
                WHERE chat_id = ?)""";
        List<Link> list = jdbcTemplate.query(sql,
                (rs, rn) -> new Link(
                        rs.getLong("link_id"),
                        rs.getString("url"),
                        rs.getTimestamp("last_update")
                ), tgChatId);
        return list;
    }
}
