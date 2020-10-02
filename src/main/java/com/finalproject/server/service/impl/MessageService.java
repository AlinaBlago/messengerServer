package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.service.MessageOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public Set<MessengerUser> getUserChats(String login) {
        Set<MessengerUser> usersToReturn = new HashSet<>();

        for(Message msg1 : messages){
            if(msg1.getSender().getUsername().equals(login) || msg1.getReceiver().getUsername().equals(login)){
                usersToReturn.add(msg1.getReceiver());
                usersToReturn.add(msg1.getSender());
            }
        }

        for(Message msg : unreadMessages){
            if(msg.getSender().getUsername().equals(login) || msg.getReceiver().getUsername().equals(login)){
                usersToReturn.add(msg.getReceiver());
                usersToReturn.add(msg.getSender());
            }
        }

        return usersToReturn;
    }

    @Override
    public List<Message> getNewMessages(Long receiverId) {
        List<Message> receiverMessages = messageRepository.getMessagesByReceiver_Id(receiverId);
        return receiverMessages.stream().filter(message -> message.isRead() == false).collect(Collectors.toList());
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
