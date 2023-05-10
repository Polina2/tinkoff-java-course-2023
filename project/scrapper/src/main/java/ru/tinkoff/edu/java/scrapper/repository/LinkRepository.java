package ru.tinkoff.edu.java.scrapper.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;

@Repository
@RequiredArgsConstructor
public class LinkRepository implements IRepository<Link> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(Link object) {
        String sql = "INSERT INTO link (url) VALUES (?)";
        jdbcTemplate.update(sql, object.url());
    }

    public Long getId(Link object) {
        return jdbcTemplate.query(
                "SELECT id FROM link WHERE url = ?",
                (rs, rn) -> rs.getLong("id"),
                object.url()
        ).get(0);
    }

    @Override
    public void remove(Link object) {
        String sql = "DELETE FROM link WHERE url = ?";
        jdbcTemplate.update(sql, object.url());
    }

    @Override
    public List<Link> findAll() {
        String sql = "SELECT * FROM link";

        return jdbcTemplate.query(sql, this::mapper);
    }

    public List<Link> findByTgChatId(Long tgChatId) {
        String sql = """
                SELECT *
                FROM link
                WHERE id IN (
                SELECT link_id
                FROM subscription
                WHERE chat_id = (
                SELECT id
                FROM tg_chat
                WHERE tg_chat_id = ?))""";
        List<Link> list = jdbcTemplate.query(sql, this::mapper, tgChatId);
        return list;
    }

    public List<Link> findNotChecked() {
        String sql = "SELECT * FROM link WHERE CURRENT_TIMESTAMP - last_check > '02:00:00'";
        List<Link> list = jdbcTemplate.query(sql, this::mapper);

        for (Link link : list) {
            String sqlUpdate = "UPDATE link SET last_check = current_timestamp WHERE id = ?";
            jdbcTemplate.update(sqlUpdate, link.id());
        }

        return list;
    }

    public void updateLink(Link link, Timestamp lastUpdate, String updateInfo) {
        jdbcTemplate.update(
                "UPDATE link SET last_update = ?, update_info = ? WHERE id = ?", lastUpdate, updateInfo, link.id()
        );
    }

    private Link mapper(ResultSet rs, int rn) throws SQLException {
        return new Link(
            rs.getLong("id"),
            rs.getString("url"),
            rs.getTimestamp("last_update"),
            rs.getTimestamp("last_check"),
            rs.getString("update_info")
        );
    }
}
