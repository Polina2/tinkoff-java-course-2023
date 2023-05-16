package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.util.Collection;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);

    Collection<TgChat> listAll(URI link);
}
