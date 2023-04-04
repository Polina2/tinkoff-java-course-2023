package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubReposResponse;

public class GitHubClient {

    @Autowired
    private WebClient webClient;

    public Mono<GitHubReposResponse> getRepository(String repository){
        String path = "/repos/" + repository;
        Mono<GitHubReposResponse> response = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(GitHubReposResponse.class);
        return response;
    }
}
