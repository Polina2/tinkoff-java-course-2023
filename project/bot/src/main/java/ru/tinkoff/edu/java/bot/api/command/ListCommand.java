package ru.tinkoff.edu.java.bot.api.command;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command{

    private final ScrapperClient client;
    private Command successor;

    @Autowired
    @Qualifier("startCommand")
    public void setSuccessor(Command successor){
        this.successor = successor;
    }
    @Override
    public Command successor() {
        return this.successor;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Получить список отслеживаемых ссылок";
    }

    @Override
    public String createReply(Update update){
        ListLinksResponse response = client.getLinks(update.message().chat().id()).block();
        return createReply(response);
    }

    public String createReply(ListLinksResponse response){
        boolean hasLinks = response.links().size() > 0;
        StringBuilder text = new StringBuilder();
        if (hasLinks){
            text.append("Отслеживаемые ссылки:");
            for (LinkResponse linkResponse : response.links()){
                text.append('\n').append(linkResponse.url());
            }
        } else {
            text.append("У вас нет отслеживаемых ссылок");
        }
        return text.toString();
    }
}
