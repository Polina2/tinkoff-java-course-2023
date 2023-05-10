package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    Collection<Link> listAll(long tgChatId);

    Collection<Link> listNotChecked();

    void updateLink(Link link, Timestamp lastUpdate, String updateInfo);
}
