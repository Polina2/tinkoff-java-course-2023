package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {

    private final JpaTgChatRepository tgChatRepository;

    @Transactional
    @Override
    public void register(long tgChatId) {
        ru.tinkoff.edu.java.scrapper.entity.TgChat tgChat = new ru.tinkoff.edu.java.scrapper.entity.TgChat();
        tgChat.setTgChatId(tgChatId);
        tgChatRepository.save(tgChat);
    }

    @Transactional
    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.deleteByTgChatId(tgChatId);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<TgChat> listAll(URI link) {
        return tgChatRepository.findByLinks_Url(link.toString()).stream()
                .map(tgChat -> new TgChat(tgChat.getId(), tgChat.getTgChatId())).toList();
    }
}
