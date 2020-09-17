package com.finalproject.server.service;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MessageService {

    Iterable<Message> findAll();
    Optional<Message> findMessageById(Long id);
    void deleteMessageById(Long id);
    Message add(Message message);
}
