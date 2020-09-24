package com.finalproject.server.service;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MessageOperations {

    Iterable<Message> findAll();
    Optional<Message> findMessageById(Long id);
    void deleteMessageById(Long id);
    Message add(Message message);
    Set<User> getUserChats(String login);
    List<Message> getNewMessages(Long receiverId);
    List<Message> getChat(Long receiverId, Long senderId);
    Long save(Message message);
    void updateAll(Iterable<Message> messages);
}
