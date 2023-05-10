package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;

@Component
@RequiredArgsConstructor
public class BotClient {

    private final WebClient botWebClient;

    public void sendUpdate(LinkUpdate linkUpdate) {
        String path = "/updates";
        botWebClient.post().uri(path).bodyValue(linkUpdate).retrieve().bodyToMono(Void.class).subscribe();
    }
}
