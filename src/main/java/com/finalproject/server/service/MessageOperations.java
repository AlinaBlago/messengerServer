package com.finalproject.server.service;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.GetChatRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MessageOperations {

    Iterable<Message> findAll();
    Optional<Message> findMessageById(Long id);
    void deleteMessageById(Long id);
    Message add(Optional<MessengerUser> sender, SendMessageRequest request);
    List<Message> loadChat(Optional<MessengerUser> user, GetChatRequest request);
//  List<Message> getNewMessages(Long receiverId);
    void updateAll(Iterable<Message> messages);
}
