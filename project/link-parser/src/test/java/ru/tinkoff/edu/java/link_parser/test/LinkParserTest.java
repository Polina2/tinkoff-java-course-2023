package ru.tinkoff.edu.java.link_parser.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverflowLinkParser;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LinkParserTest {
    GitHubLinkParser gitHubLinkParser = new GitHubLinkParser();
    StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

    @Parameterized.Parameter
    public String link;
    @Parameterized.Parameter(1)
    public String result;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][] {
                {"https://github.com/Polina2/SeaBattle", "Polina2/SeaBattle"},
                {"https://github.com/Polina2/SeaBattle/tree/main/src/main/java", "Polina2/SeaBattle"},
                {"https://github.com/pulls/assigned", "pulls/assigned"},
                {"https://stackoverflow.com/questions/345626/how-can-i-avoid-using-exceptions-for-flow-control", "345626"},
                {"https://stackoverflow.com/questions/3475492", "3475492"},
                {"https://github.com/Polina2", null},
                {"https://github.com/Polina_2/SeaBattle", null},
                {"https://github.blog/changelog/", null},
                {"abc", null},
                {"https://stackoverflow.com/questions", null},
                {"https://stackoverflow.com/jobs/companies", null},
                {"https://stackoverflow.com/posts/7593304/timeline", null}
        });
    }

    @Test
    public void linkParserTest(){
        Assertions.assertEquals(result, stackOverflowLinkParser.parse(link));
    }
}
