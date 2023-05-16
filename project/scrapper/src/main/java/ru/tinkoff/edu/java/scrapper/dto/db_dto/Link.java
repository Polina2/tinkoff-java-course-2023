package ru.tinkoff.edu.java.scrapper.dto.db_dto;

import java.sql.Timestamp;

public record Link(Long id, String url, Timestamp lastUpdate, Timestamp lastCheck, String updateInfo) {

    public Link(String url) {
        this(null, url, null, null, null);
    }
}
