package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubReposResponse;

@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient webClient;

    public Mono<GitHubReposResponse> getRepository(String repository){
        String path = "/repos/" + repository;
        Mono<GitHubReposResponse> response = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(GitHubReposResponse.class);
        return response;
    }
}
