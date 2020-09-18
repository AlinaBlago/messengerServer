package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.service.MessageOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService implements MessageOperations {

    List<Message> messages = new ArrayList<>();
    List<Message> unreadMessages = new ArrayList<>();

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

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

    @Override
    public Set<User> getUserChats(Long id) {
        Set<User> usersToReturn = new HashSet<>();

        for(Message msg1 : messages){
            if(msg1.getSender().getId() == id || msg1.getReceiver().getId() == id){
                usersToReturn.add(msg1.getReceiver());
                usersToReturn.add(msg1.getSender());
            }
        }

        for(Message msg : unreadMessages){
            if(msg.getSender().getId() == id || msg.getReceiver().getId() == id){
                usersToReturn.add(msg.getReceiver());
                usersToReturn.add(msg.getSender());
            }
        }

        return usersToReturn;
    }

    @Override
    public List<Message> getNewMessagesByReceiverAndReadFalse(Long receiverId) {
        return messageRepository.getMessagesByReceiverAndReadFalse(receiverId);
    }

    @Override
    public List<Message> getChat(Long receiverId, Long senderId) {
        return messageRepository.getMessagesByReceiver_IdAndSender_Id(receiverId, senderId);
    }

    @Override
    public Long save(Message message) {
        return messageRepository.save(message).getId();
    }

    @Override
    public void updateAll(Iterable<Message> messages) {
        messageRepository.saveAll(messages);
    }

}
