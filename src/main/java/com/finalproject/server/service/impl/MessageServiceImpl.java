package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Message;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> findMessageById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message add(Message message) {
        return messageRepository.save(message);
    }

}
