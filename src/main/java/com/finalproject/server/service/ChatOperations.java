package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.ChatResponse;

import java.util.List;
import java.util.Optional;

public interface ChatOperations {
    Iterable<Chat> findAll();
    Optional<Chat> findById(Long id);
    void deleteById(Long id);
    Chat save(Chat chat);
    ChatResponse addChat(Optional<MessengerUser> user, UserRequest request);
    void updateAll(Iterable<Chat> chats);
    List<Chat> findAllByIdFirst(MessengerUser user);
    List<Chat> findAllByIdSecond(MessengerUser user);

}
