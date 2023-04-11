package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tinkoff.edu.java.bot.api.command.HelpCommand;
import ru.tinkoff.edu.java.bot.api.command.ListCommand;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ListCommandTest {
    private final ListCommand command = new ListCommand(null);

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

        String expectedMessage = "Отслеживаемые ссылки:\nhttps://github.com/sanyarnd/tinkoff-java-course-2022\nhttps://stackoverflow.com/questions/21323309";
        Assertions.assertNotNull(message);
        Assertions.assertEquals(expectedMessage, message);
    }

    @Test
    public void emptyListOfLinks(){
        ListLinksResponse response = new ListLinksResponse(new ArrayList<>());

        String message = command.createReply(response);

        String expectedMessage = "У вас нет отслеживаемых ссылок";
        Assertions.assertNotNull(message);
        Assertions.assertEquals(expectedMessage, message);
    }



    @Test
    public void nonCommandMessage(){
        String text = "/abc";
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn(text);

        String reply = new HelpCommand().handle(update);

        Assertions.assertNotNull(reply);
        String expectedReply = "Я вас не понимаю. Для получения списка доступных команд введите /help";
        Assertions.assertEquals(expectedReply, reply);
    }
}
