package ru.tinkoff.edu.java.scrapper.dto.db_dto;

import java.sql.Timestamp;

public record Link (Long id, String url, Timestamp last_update, Long tgChatId) {

    public Link(Long id, String url, Timestamp last_update){
        this(id, url, last_update, null);
    }
}
