package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public WebClient gitHubWebClient(@Value("${app.gitBaseUrl}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient stackOverflowWebClient(@Value("${app.stackoverflowBaseUrl}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}