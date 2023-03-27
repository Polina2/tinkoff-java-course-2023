package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.ListAnswersResponse;

@RequiredArgsConstructor
public class StackOverflowClient {

    private static final String DEFAULT_URL = "https://api.stackexchange.com";

    private final WebClient webClient;

    public static StackOverflowClient create(){
        return create(DEFAULT_URL);
    }

    public static StackOverflowClient create(String baseUrl){
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        return new StackOverflowClient(client);
    }

    public Mono<ListAnswersResponse> getAnswers(String questionId){
        String path = "/2.3/questions/"+ questionId +"/answers?site=stackoverflow";
        Mono<ListAnswersResponse> response = webClient.get()
                .uri(path)
                .retrieve()
                .bodyToMono(ListAnswersResponse.class);
        return response;
    }
}
