package ru.tinkoff.edu.java.scrapper.dto.db_dto;

import java.sql.Timestamp;

public record Link (Long id, String url, Timestamp last_update, Timestamp last_check, Long tgChatId) {

    public Link(Long id, String url, Timestamp last_update, Timestamp last_check){
        this(id, url, last_update, last_check, null);
    }

    public Link(String url, Long tgChatId){
        this(null, url, null, null, tgChatId);
    }
}
