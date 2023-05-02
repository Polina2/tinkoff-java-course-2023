package ru.tinkoff.edu.java.scrapper.configuration;

public record RabbitMQProperties(String exchange, String queue, String routingKey) {
}
