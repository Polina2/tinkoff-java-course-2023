package ru.tinkoff.edu.java.scrapper.dto.db_dto;

public record TgChat(Long id, Long tgChatId) {
    public TgChat(Long tgChatId) {
        this(null, tgChatId);
    }
}
