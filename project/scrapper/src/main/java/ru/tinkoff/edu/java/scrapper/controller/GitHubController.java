package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubReposResponse;

@RequestMapping("/github")
@RestController
public class GitHubController {

    private final GitHubClient client = GitHubClient.create();

    @GetMapping("/{owner}/{repo}")
    public Mono<GitHubReposResponse> getRepository(@PathVariable String owner, @PathVariable String repo){
        return client.getRepository(owner+'/'+repo);
    }
}
