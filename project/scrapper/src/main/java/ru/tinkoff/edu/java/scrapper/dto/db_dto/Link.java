package ru.tinkoff.edu.java.scrapper.dto.db_dto;

import java.sql.Timestamp;

public record Link (Long id, String url, Timestamp last_update, Timestamp last_check, String update_info) {

    public Link(String url){
        this(null, url, null, null, null);
    }
}
