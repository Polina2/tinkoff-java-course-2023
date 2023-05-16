package ru.tinkoff.edu.java.bot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

@Component
@RequiredArgsConstructor
public class ScrapperClient {

    private final WebClient webClient;
    private static final String TG_CHAT_PATH = "/tg-chat/{id}";
    private static final String LINK_PATH = "/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";

    public void addChat(Long id) {
        webClient.post().uri(TG_CHAT_PATH, id).retrieve().bodyToMono(Void.class).subscribe();
    }

    public void deleteChat(Long id) {
        webClient.delete().uri(TG_CHAT_PATH, id).retrieve().bodyToMono(Void.class).subscribe();
    }

    public Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
                .uri(LINK_PATH)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .bodyValue(addLinkRequest)
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
                .uri(LINK_PATH)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .bodyValue(removeLinkRequest)
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> getLinks(Long tgChatId) {
        return webClient.get()
                .uri(LINK_PATH)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }
}
