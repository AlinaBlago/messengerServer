package com.finalproject.server.service;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface MessageService {

    Iterable<Message> findAll();
    Optional<Message> findMessageById(Long id);
    void deleteMessageById(Long id);
    Message add(Message message);
    Set<User> getUserChats(Long id);
    List<Message> getMessagesBySender(Long id);
    List<Message> getNewMessagesByReceiverAndReadFalse(Long receiverId);
    List<Message> getChat(Long receiverId, Long senderId);
}
