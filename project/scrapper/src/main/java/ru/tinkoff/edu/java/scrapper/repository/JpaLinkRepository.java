package ru.tinkoff.edu.java.scrapper.repository;

import java.sql.Timestamp;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    void deleteByUrl(String url);

    @NotNull List<Link> findAll();

    Link findByUrl(String url);

    List<Link> findByTgChatsTgChatId(Long tgChatId);

    List<Link> findByLastCheckLessThan(Timestamp maxTime);

}
