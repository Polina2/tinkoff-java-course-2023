package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.ListAnswersResponse;

public class StackOverflowClient {

    @Autowired
    private WebClient webClient;

    public Mono<ListAnswersResponse> getAnswers(String questionId){
        String path = "/2.3/questions/"+ questionId +"/answers?site=stackoverflow";
        Mono<ListAnswersResponse> response = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ListAnswersResponse.class);
        return response;
    }
}
