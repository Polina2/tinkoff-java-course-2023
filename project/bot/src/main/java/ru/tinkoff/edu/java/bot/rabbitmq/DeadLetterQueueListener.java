package ru.tinkoff.edu.java.bot.rabbitmq;

import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Log
@RabbitListener(queues = "{${app.rabbitmq.queue}" + ".dlq")
public class DeadLetterQueueListener {

    @RabbitHandler
    public void processFailedMessages(Object message) {
        log.info("Got dead letter");
    }
}
