package ru.tinkoff.edu.java.bot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

@Component
@RequiredArgsConstructor
public class ScrapperClient {

    private final WebClient webClient;

    public void addChat(Long id){
        String path = "/tg-chat/{id}";
        webClient.post().uri(path, id).retrieve();
    }

    public void deleteChat(Long id){
        String path = "/tg-chat/{id}";
        webClient.delete().uri(path, id).retrieve();
    }

    public void addLink(Long tgChatId, AddLinkRequest addLinkRequest){
        String path = "/links";
        webClient.post()
                .uri(path)
                .header("Tg-Chat-Id", tgChatId.toString())
                .bodyValue(addLinkRequest)
                .retrieve();
    }

    public void deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest){
        String path = "/links";
        webClient.method(HttpMethod.DELETE)
                .uri(path)
                .header("Tg-Chat-Id", tgChatId.toString())
                .bodyValue(removeLinkRequest)
                .retrieve();
    }

    public Mono<ListLinksResponse> getLinks(Long tgChatId){
        String path = "/links";
        return webClient.get()
                .uri(path)
                .header("Tg-Chat-Id", tgChatId.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }
}