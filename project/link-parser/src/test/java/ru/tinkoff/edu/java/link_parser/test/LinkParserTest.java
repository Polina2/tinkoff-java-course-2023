package ru.tinkoff.edu.java.link_parser.test;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.link_parser.GitHubLinkParser;
import ru.tinkoff.edu.java.link_parser.StackOverflowLinkParser;

public class LinkParserTest {
    GitHubLinkParser gitHubLinkParser = new GitHubLinkParser();
    StackOverflowLinkParser stackOverflowLinkParser = new StackOverflowLinkParser(gitHubLinkParser);

    @Test
    public void linkParserTest(){
        String[] links = {
                "https://github.com/Polina2",
                "https://github.com/Polina2/SeaBattle",
                "https://github.com/Polina2/SeaBattle/tree/main/src/main/java",
                "https://github.blog/changelog/",
                "https://github.com/pulls/assigned",

                "abc",
                "https://stackoverflow.com/questions/345626/how-can-i-avoid-using-exceptions-for-flow-control",
                "https://stackoverflow.com/questions/3475492",
                "https://stackoverflow.com/questions",
                "https://stackoverflow.com/jobs/companies",
                "https://stackoverflow.com/posts/7593304/timeline",

                "https://github.com/Polina_2/SeaBattle"
        };

        String[] results = new String[links.length];

        for (int i = 0; i < links.length; i++){
            results[i] = stackOverflowLinkParser.parse(links[i]);
        }

        MatcherAssert.assertThat(results[0], Matchers.is(Matchers.nullValue()));
        MatcherAssert.assertThat(results[1], Matchers.equalTo("Polina2/SeaBattle"));
        MatcherAssert.assertThat(results[2], Matchers.equalTo("Polina2/SeaBattle"));
        MatcherAssert.assertThat(results[3], Matchers.is(Matchers.nullValue()));
        MatcherAssert.assertThat(results[4], Matchers.equalTo("pulls/assigned"));

        MatcherAssert.assertThat(results[5], Matchers.is(Matchers.nullValue()));
        MatcherAssert.assertThat(results[6], Matchers.equalTo("345626"));
        MatcherAssert.assertThat(results[7], Matchers.equalTo("3475492"));
        MatcherAssert.assertThat(results[8], Matchers.is(Matchers.nullValue()));
        MatcherAssert.assertThat(results[9], Matchers.is(Matchers.nullValue()));
        MatcherAssert.assertThat(results[10], Matchers.is(Matchers.nullValue()));

        MatcherAssert.assertThat(results[11], Matchers.is(Matchers.nullValue()));
    }
}
