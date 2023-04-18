package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;

import java.net.URI;
import java.util.Collection;

public interface LinkService {
    void add(long tgChatId, URI url);
    void remove(long tgChatId, URI url);
    Collection<Link> listAll(long tgChatId);
}
