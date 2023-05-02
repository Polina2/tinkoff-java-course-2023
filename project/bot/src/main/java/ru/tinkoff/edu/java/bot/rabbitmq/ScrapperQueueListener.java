package ru.tinkoff.edu.java.bot.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.SendUpdatesService;

@RabbitListener(queues = "${app.rabbitmq.queue}")
@Component
@RequiredArgsConstructor
public class ScrapperQueueListener {

    private final SendUpdatesService sendUpdatesService;

    @RabbitHandler
    public void receiver(LinkUpdate update) {
        sendUpdatesService.sendUpdate(update);
    }
}