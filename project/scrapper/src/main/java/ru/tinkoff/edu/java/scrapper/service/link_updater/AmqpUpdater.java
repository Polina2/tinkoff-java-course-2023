package ru.tinkoff.edu.java.scrapper.service.link_updater;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.rabbitmq.ScrapperQueueProducer;

@RequiredArgsConstructor
public class AmqpUpdater implements LinkUpdater {

    private final ScrapperQueueProducer scrapperQueueProducer;

    @Override
    public void update(LinkUpdate linkUpdate) {
        scrapperQueueProducer.send(linkUpdate);
    }
}
