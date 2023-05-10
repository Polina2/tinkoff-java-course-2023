package ru.tinkoff.edu.java.scrapper.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;

@RequiredArgsConstructor
@Repository
public class TgChatRepository implements IRepository<TgChat> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(TgChat object) {
        String sql = "INSERT INTO tg_chat (tg_chat_id) VALUES (?)";
        jdbcTemplate.update(sql, object.tgChatId());
    }

    public Long getId(TgChat object) {
        return jdbcTemplate.query(
                "SELECT id FROM tg_chat WHERE tg_chat_id = ?",
                (rs, rn) -> rs.getLong("id"),
                object.tgChatId()
        ).get(0);
    }

    @Override
    public void remove(TgChat object) {
        String sql = "DELETE FROM tg_chat WHERE tg_chat_id = ?";
        jdbcTemplate.update(sql, object.tgChatId());
    }

    @Override
    public List<TgChat> findAll() {
        String sql = "SELECT * FROM tg_chat";
        return jdbcTemplate.query(
                sql, this::mapper
        );
    }

    public List<TgChat> findByLink(String url) {
        String sql = """
                SELECT * FROM tg_chat
                WHERE id = (
                SELECT chat_id FROM subscription
                WHERE link_id = (
                SELECT id FROM link
                WHERE url = ?))
                """;
        return jdbcTemplate.query(
                sql, this::mapper, url
        );
    }

    private TgChat mapper(ResultSet rs, int rn) throws SQLException {
        return new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id"));
    }
}
