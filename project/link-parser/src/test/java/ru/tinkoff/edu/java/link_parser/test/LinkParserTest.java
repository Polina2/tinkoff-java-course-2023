package ru.tinkoff.edu.java.link_parser.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverflowLinkParser;

public class LinkParserTest {
    GitHubLinkParser gitHubLinkParser = new GitHubLinkParser();
    StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

    @ParameterizedTest
    @CsvSource({
            "https://github.com/Polina2/SeaBattle, Polina2/SeaBattle",
            "https://github.com/Polina2/SeaBattle/tree/main/src/main/java, Polina2/SeaBattle",
            "https://github.com/pulls/assigned, pulls/assigned",
            "https://stackoverflow.com/questions/345626/how-can-i-avoid-using-exceptions-for-flow-control, 345626",
            "https://stackoverflow.com/questions/3475492, 3475492"
    })
    public void notNullAnswerTest(String link, String expected){
        Assertions.assertEquals(expected, stackOverflowLinkParser.parse(link));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://github.com/Polina2",
            "https://github.com/Polina_2/SeaBattle",
            "https://github.blog/changelog/",
            "abc",
            "https://stackoverflow.com/questions",
            "https://stackoverflow.com/jobs/companies",
            "https://stackoverflow.com/posts/7593304/timeline"
    })
    @EmptySource
    public void nullAnswerTest(String link){
        Assertions.assertNull(stackOverflowLinkParser.parse(link));
    }
}
