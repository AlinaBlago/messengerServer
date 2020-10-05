package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.AddChatRequest;
import com.finalproject.server.payload.response.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ChatOperations {
    Iterable<Chat> findAll();
    Optional<Chat> findById(Long id);
    void deleteById(Long id);
    Chat save(Chat chat);
    ChatResponse addChat(Optional<MessengerUser> user, AddChatRequest request);
    void updateAll(Iterable<Chat> chats);

//    Optional<Chat> findByIdFirst(Long id);
//    Optional<Chat> findByIdSecond(Long id);
    List<Chat> findAllByIdFirst(MessengerUser user);
    List<Chat> findAllByIdSecond(MessengerUser user);
}
