package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.LinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverflowLinkParser;

@Configuration
public class LinkParsersConfiguration {

    @Bean
    public LinkParser linkParser(){
        return new StackOverflowLinkParser(new GitHubLinkParser());
    }
}
