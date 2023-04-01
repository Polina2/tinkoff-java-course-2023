package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient gitHubClient(){
        return new GitHubClient(gitHubWebClient());
    }

    public WebClient gitHubWebClient(){
        return webClient("${app.gitBaseUrl}");
    }

    @Bean
    public StackOverflowClient stackOverflowClient(){
        return new StackOverflowClient(stackOverflowWebClient());
    }

    public WebClient stackOverflowWebClient(){
        return webClient("${app.stackoverflowBaseUrl}");
    }

    public WebClient webClient(String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
