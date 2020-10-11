package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.MessageResponse;

import java.util.List;
import java.util.Optional;

public interface MessageOperations {

    Iterable<Message> findAll();
    Optional<Message> findMessageById(Long id);
    void deleteMessageById(Long id);
    Message add(Optional<MessengerUser> sender, SendMessageRequest request);
    List<Message> loadChat(Optional<MessengerUser> user, UserRequest request);
//  List<Message> getNewMessages(Long receiverId);
    void updateAll(Iterable<Message> messages);
    public List<MessageResponse> getNewMessages(Chat chat , MessengerUser receiver );
}
