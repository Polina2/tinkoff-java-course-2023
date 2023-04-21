package ru.tinkoff.edu.java.bot.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.bot.api.UserMessageProcessor;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserMessageProcessorTest {
    @Autowired
    private UserMessageProcessor processor;

    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;

    @Test
    public void nonCommandMessage(){
        String text = "/abc";
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.text()).thenReturn(text);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);

        String reply = processor.createReply(update);

        Assertions.assertNotNull(reply);
        String expectedReply = "Я вас не понимаю. Для получения списка доступных команд введите /help";
        Assertions.assertEquals(expectedReply, reply);
    }
}
