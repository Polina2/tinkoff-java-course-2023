package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public WebClient gitHubWebClient(@Value("${app.gitBaseUrl:https://api.github.com}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient stackOverflowWebClient(@Value("${app.stackoverflowBaseUrl:https://api.stackexchange.com}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public WebClient botWebClient(@Value("${app.botBaseUrl:http://localhost:8180}") String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
