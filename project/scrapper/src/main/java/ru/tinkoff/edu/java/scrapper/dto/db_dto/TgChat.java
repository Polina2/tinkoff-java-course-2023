package ru.tinkoff.edu.java.scrapper.dto.db_dto;

public record TgChat(Long id, String name) {
    public TgChat(String name){
        this(null, name);
    }
}
