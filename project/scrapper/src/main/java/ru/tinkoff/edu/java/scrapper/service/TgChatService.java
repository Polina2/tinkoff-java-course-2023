package ru.tinkoff.edu.java.scrapper.service;

public interface TgChatService {
    void register(long tgChatId, String username);
    void unregister(long tgChatId);
}
