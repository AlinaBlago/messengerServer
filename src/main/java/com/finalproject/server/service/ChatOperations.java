package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;

import java.util.List;
import java.util.Optional;

public interface ChatOperations {
    Iterable<Chat> findAll();
    Optional<Chat> findById(Long id);
    void deleteById(Long id);
    Chat save(Chat chat);
    void updateAll(Iterable<Chat> chats);
//    Optional<Chat> findByIdFirst(Long id);
//    Optional<Chat> findByIdSecond(Long id);
    List<Chat> findAllByIdFirst(MessengerUser user);
    List<Chat> findAllByIdSecond(MessengerUser user);
}
