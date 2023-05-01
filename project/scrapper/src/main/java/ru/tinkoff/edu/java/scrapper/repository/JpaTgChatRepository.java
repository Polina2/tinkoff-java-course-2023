package ru.tinkoff.edu.java.scrapper.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

import java.util.List;

public interface JpaTgChatRepository extends JpaRepository<TgChat, Long> {
    void deleteByTgChatId(Long tgChatId);

    @NotNull List<TgChat> findAll();

    TgChat findByTgChatId(Long tgChatId);

    List<TgChat> findByLinksUrl(String url);
}
