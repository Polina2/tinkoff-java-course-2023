package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.LinkParser;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Component
@EnableScheduling
@Log
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final TgChatService tgChatService;
    private final LinkService linkService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final LinkParser linkParser;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update(){
        log.info("Updating");

        List<Link> linkList = (List<Link>) linkService.listNotChecked();

        for (Link link : linkList){
            List<Long> tgChatIds = tgChatService.listAll(URI.create(link.url())).stream().map(TgChat::tgChatId).toList();
            String res = linkParser.parse(link.url());

            if (!res.matches("\\d+")){
                GitHubReposResponse ghResponse = gitHubClient.getRepository(res).block();
                GitHubCommitResponse ghcResponse = gitHubClient.getLastCommit(res).blockFirst();
                if (!ghResponse.updatedAt().toInstant().equals(link.last_update().toInstant())){
                    botClient.sendUpdate(new LinkUpdate(
                            link.id(), URI.create(link.url()), "new push to "+ghResponse.name(), tgChatIds
                    ));
                    linkService.updateLink(link, Timestamp.valueOf(ghResponse.updatedAt().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                            link.update_info());
                }
                if (!ghcResponse.sha().equals(link.update_info())){
                    botClient.sendUpdate(new LinkUpdate(
                            link.id(), URI.create(link.url()), "new commit: "+ghcResponse.commit().message(), tgChatIds
                    ));
                    linkService.updateLink(link, Timestamp.valueOf(ghResponse.updatedAt().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                            ghcResponse.sha());
                }
            } else {
                ListAnswersResponse soResponse = stackOverflowClient.getAnswers(res).block();
                OffsetDateTime time = Collections.max(soResponse.items().stream()
                        .map((StackOverflowResponse::lastActivityDate))
                        .toList());
                if (!time.toInstant().equals(link.last_update().toInstant())){
                    if (soResponse.items().size() != Integer.parseInt(link.update_info())){
                        botClient.sendUpdate(new LinkUpdate(link.id(), URI.create(link.url()),
                                "new answer", tgChatIds));
                    }
                    botClient.sendUpdate(new LinkUpdate(link.id(), URI.create(link.url()),
                            "answer updated", tgChatIds));
                    linkService.updateLink(link, Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                            ""+soResponse.items().size());
                }
            }

        }
    }
}
