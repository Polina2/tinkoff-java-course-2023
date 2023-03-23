package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.*;

@RequestMapping("/tg-chat")
@RestController
public class ScrapperChatController {

    @PostMapping("/{id}")
    public Response addChat(@PathVariable Long id){
        return new Response("Чат зарегистрирован");
    }

    @DeleteMapping("/{id}")
    public Response deleteChat(@PathVariable Long id){
        return new Response("Чат успешно удалён");
    }

}
