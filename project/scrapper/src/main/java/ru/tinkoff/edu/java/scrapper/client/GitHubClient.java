package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubReposResponse;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient gitHubWebClient;

    public Mono<GitHubReposResponse> getRepository(String repository){
        String path = "/repos/" + repository;
        Mono<GitHubReposResponse> response = gitHubWebClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(GitHubReposResponse.class);
        return response;
    }
}
