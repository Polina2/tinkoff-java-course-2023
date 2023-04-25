package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.db_dto.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    @Override
    public void register(long tgChatId) {
        tgChatRepository.add(new TgChat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.remove(new TgChat(tgChatId));
    }
}
