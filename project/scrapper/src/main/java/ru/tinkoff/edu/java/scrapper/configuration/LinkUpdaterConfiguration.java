package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.rabbitmq.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.service.link_updater.HttpUpdater;
import ru.tinkoff.edu.java.scrapper.service.link_updater.AmqpUpdater;

@Configuration
public class LinkUpdaterConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "true")
    public LinkUpdater amqpUpdater(ScrapperQueueProducer producer){
        return new AmqpUpdater(producer);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "false")
    public LinkUpdater httpUpdater(BotClient botClient){
        return new HttpUpdater(botClient);
    }
}
