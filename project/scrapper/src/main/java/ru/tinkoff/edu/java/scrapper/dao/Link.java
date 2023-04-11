package ru.tinkoff.edu.java.scrapper.dao;

import java.sql.Timestamp;

public record Link(Long link_id, String url, Timestamp last_update) {
}
