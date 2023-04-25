package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/tg-chat")
@RestController
public class ScrapperChatController {

    @PostMapping("/{id}")
    public void addChat(@PathVariable Long id){
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable Long id){
    }

}
