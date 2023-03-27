package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient gitHubClient(){
        return GitHubClient.create();
    }

    @Bean
    public StackOverflowClient stackOverflowClient(){
        return StackOverflowClient.create();
    }
}
