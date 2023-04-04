package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.bot.api.command.HelpCommand;
import ru.tinkoff.edu.java.bot.api.command.ListCommand;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ListCommandTest {
    private final ListCommand command = new ListCommand();
    @Mock
    Update update;
    @Mock
    Message message;

    @Test
    public void listOfLinks(){
        ListLinksResponse response = new ListLinksResponse(
                List.of(new LinkResponse(1L, URI.create(
                                        "https://github.com/sanyarnd/tinkoff-java-course-2022"
                        )),
                        new LinkResponse(2L, URI.create(
                                        "https://stackoverflow.com/questions/21323309"
                        )))
        );

        String message = command.createReply(response);

        MatcherAssert.assertThat(message, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(message, Matchers.equalTo(
                "Отслеживаемые ссылки:\nhttps://github.com/sanyarnd/tinkoff-java-course-2022\nhttps://stackoverflow.com/questions/21323309"
        ));
    }

    @Test
    public void emptyListOfLinks(){
        ListLinksResponse response = new ListLinksResponse(new ArrayList<>());

        String message = command.createReply(response);

        MatcherAssert.assertThat(message, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(message, Matchers.equalTo("У вас нет отслеживаемых ссылок"));
    }



    @Test
    public void nonCommandMessage(){
        String text = "/abc";
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn(text);

        String reply = new HelpCommand().handle(update);

        MatcherAssert.assertThat(reply, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(reply, Matchers.equalTo("Я вас не понимаю. Для получения списка доступных команд введите /help"));
    }
}
