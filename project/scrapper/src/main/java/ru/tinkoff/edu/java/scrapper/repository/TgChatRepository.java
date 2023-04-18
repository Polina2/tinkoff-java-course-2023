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
        if (object.id() != null){
            String sql = "INSERT INTO tg_chat VALUES (?, ?)";
            jdbcTemplate.update(sql, object.id(), object.name());
        } else {
            String sql = "INSERT INTO tg_chat (name) VALUES (?)";
            jdbcTemplate.update(sql, object.name());
        }
    }

    @Override
    public void remove(TgChat object) {
        String sql = "DELETE FROM tg_chat WHERE chat_id = ?";
        jdbcTemplate.update(sql, object.id());
    }

    @Override
    public List<TgChat> findAll() {
        String sql = "SELECT * FROM tg_chat";
        List<TgChat> list = jdbcTemplate.query(
                sql, (rs, rn) -> new TgChat(rs.getLong("chat_id"), rs.getString("name")));
        return list;
    }
}
