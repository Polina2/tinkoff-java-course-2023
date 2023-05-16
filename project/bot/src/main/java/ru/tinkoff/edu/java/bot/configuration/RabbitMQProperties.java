package ru.tinkoff.edu.java.bot.configuration;

public record RabbitMQProperties(String exchange, String queue, String routingKey) {
}
