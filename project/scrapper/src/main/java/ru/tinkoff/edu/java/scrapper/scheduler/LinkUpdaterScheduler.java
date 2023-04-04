package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

@EnableScheduling
public class LinkUpdaterScheduler {

    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update(){
        LOGGER.info("Updating");
    }
}
