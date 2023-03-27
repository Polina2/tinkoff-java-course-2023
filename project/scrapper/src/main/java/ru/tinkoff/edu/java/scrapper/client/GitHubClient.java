package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubReposResponse;

@RequiredArgsConstructor
public class GitHubClient {
    private static final String DEFAULT_URL = "https://api.github.com/";

    private final WebClient webClient;

    public static GitHubClient create(){
        return create(DEFAULT_URL);
    }

    public static GitHubClient create(String baseUrl){
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        return new GitHubClient(client);
    }

    public Mono<GitHubReposResponse> getRepository(String repository){
        String path = "/repos/" + repository;
        Mono<GitHubReposResponse> response = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(GitHubReposResponse.class);
        return response;
    }
}
