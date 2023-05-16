package ru.tinkoff.edu.java.scrapper.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;
    @Value("${app.rabbitmq.exchange}")
    private String exchange;
    @Value("${app.rabbitmq.routingKey}")
    private String routingKey;
    public void send(LinkUpdate update) {
        rabbitTemplate.convertAndSend(exchange, routingKey, update);
    }
}
