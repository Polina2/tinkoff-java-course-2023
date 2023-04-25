package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TgChatRepository implements IRepository<TgChat>{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(TgChat object) {
        String sql = "INSERT INTO tg_chat (tg_chat_id) VALUES (?)";
        jdbcTemplate.update(sql, object.tgChatId());
    }

    @Override
    public void remove(TgChat object) {
        String sql = "DELETE FROM tg_chat WHERE tg_chat_id = ?";
        jdbcTemplate.update(sql, object.tgChatId());
    }

    @Override
    public List<TgChat> findAll() {
        String sql = "SELECT * FROM tg_chat";
        List<TgChat> list = jdbcTemplate.query(
                sql, (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")));
        return list;
    }

    public List<TgChat> findByLink(String url){
        String sql = """
                SELECT * FROM tg_chat
                WHERE id = (
                SELECT chat_id FROM subscription
                WHERE link_id = (
                SELECT id FROM link
                WHERE url = ?))
                """;
        return jdbcTemplate.query(
                sql, (rs, rn) -> new TgChat(rs.getLong("id"), rs.getLong("tg_chat_id")), url
        );
    }
}
