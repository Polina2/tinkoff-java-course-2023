package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.ListAnswersResponse;
@Component
@RequiredArgsConstructor
public class StackOverflowClient {

    private final WebClient stackOverflowWebClient;

    public Mono<ListAnswersResponse> getAnswers(String questionId){
        String path = "/2.3/questions/"+ questionId +"/answers?site=stackoverflow";
        Mono<ListAnswersResponse> response = stackOverflowWebClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ListAnswersResponse.class);
        return response;
    }
}
