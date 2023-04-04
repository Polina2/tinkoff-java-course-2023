package ru.tinkoff.edu.java.bot.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class BotImplementation {

    private final TelegramBot bot;
    private final UserMessageProcessor ump = new UserMessageProcessor();
    public void start(){
        bot.setUpdatesListener(list -> {
            for (Update update : list){
                SendMessage message = ump.process(update);
                SendResponse response = bot.execute(message);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public BotImplementation(String token){
        bot = new TelegramBot(token);
    }
}
