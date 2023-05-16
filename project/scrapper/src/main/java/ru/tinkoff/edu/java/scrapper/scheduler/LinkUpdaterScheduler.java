package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.link_parser.LinkParser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
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
    private final LinkUpdater linkUpdater;
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
                if (link.last_update() == null || !ghResponse.updatedAt().toInstant().equals(link.last_update().toInstant())){
                    linkUpdater.update(new LinkUpdate(
                            link.id(), URI.create(link.url()), "new push to "+ghResponse.name(), tgChatIds
                    ));
                    linkService.updateLink(link, Timestamp.valueOf(ghResponse.updatedAt().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                            link.update_info());
                }
                if (!ghcResponse.sha().equals(link.update_info())){
                    linkUpdater.update(new LinkUpdate(
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
                if (link.last_update() == null || !time.toInstant().equals(link.last_update().toInstant())){
                    if (link.update_info()==null || soResponse.items().size() != Integer.parseInt(link.update_info())){
                        linkUpdater.update(new LinkUpdate(link.id(), URI.create(link.url()),
                                "new answer", tgChatIds));
                    } else {
                        linkUpdater.update(new LinkUpdate(link.id(), URI.create(link.url()),
                                "answer updated", tgChatIds));
                    }
                    linkService.updateLink(link, Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()),
                            ""+soResponse.items().size());
                }
            }

        }
    }
}
