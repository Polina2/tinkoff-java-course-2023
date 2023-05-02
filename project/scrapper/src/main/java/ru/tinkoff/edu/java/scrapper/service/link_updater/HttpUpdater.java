package ru.tinkoff.edu.java.scrapper.service.link_updater;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

@RequiredArgsConstructor
public class HttpUpdater implements LinkUpdater {

    private final BotClient botClient;

    @Override
    public void update(LinkUpdate linkUpdate) {
        botClient.sendUpdate(linkUpdate);
    }
}
