package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MessageServiceImpl implements MessageService {

    List<Message> messages = new ArrayList<>();
    List<Message> unreadMessages = new ArrayList<>();
    UserServiceImpl userService = new UserServiceImpl();

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
    public List<Message> getMessagesBySender(Long id) {
        return messageRepository.getMessagesById_sender(id);
    }

    @Override
    public List<Message> getNewMessagesByReceiverAndReadFalse(Long receiverId) {
        return messageRepository.getMessagesByReceiverAndReadFalse(receiverId);
    }

    @Override
    public List<Message> getChat(Long receiverId, Long senderId) {
        return messageRepository.getMessagesByReceiver_IdAndSender_Id(receiverId, senderId);
    }

}
