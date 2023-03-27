package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.ListAnswersResponse;

@RequestMapping("/stackoverflow")
@RestController
public class StackOverflowController {

    private final StackOverflowClient client = StackOverflowClient.create();

    @GetMapping("/{questionId}")
    public Mono<ListAnswersResponse> getAnswers(@PathVariable String questionId){
        return client.getAnswers(questionId);
    }
}
