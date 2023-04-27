package ru.tinkoff.edu.java.scrapper.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Subscription;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository implements IRepository<Subscription>{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(Subscription object) {
        String sql = "INSERT INTO subscription VALUES (?, ?)";
        jdbcTemplate.update(sql, object.chatId(), object.linkId());
    }

    @Override
    public void remove(Subscription object) {
        String sql = "DELETE FROM subscription WHERE chat_id = ? AND link_id = ?";
        jdbcTemplate.update(sql, object.chatId(), object.linkId());
    }

    @Override
    public List<Subscription> findAll() {
        String sql = "SELECT * FROM subscription";
        return jdbcTemplate.query(sql,
                (rs, rn) -> new Subscription(rs.getLong("chat_id"), rs.getLong("link_id")));
    }
}
