package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.GetChatRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.repository.ChatRepository;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.MessageOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MessageService implements MessageOperations {

    List<Message> messages = new ArrayList<>();
    List<Message> unreadMessages = new ArrayList<>();

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
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
    public Message add(Optional<MessengerUser> sender, SendMessageRequest request) {
        Optional<MessengerUser> receiver = userRepository.findByUsername(request.getReceiver());
        Message message = new Message();

        if(receiver.isPresent()) {
            Chat chat = chatRepository.findChatByFirstUserAndSecondUser(sender.get(), receiver.get());

            if(chat != null){
                message.setBody(request.getMessage());
                message.setSender(sender.get());
                message.setDate(new Date(System.currentTimeMillis()));
                message.setChat(chat);
                message.setRead(false);

                messageRepository.save(message);
            }
        }

        return message;
    }

    @Override
    public List<Message> loadChat(Optional<MessengerUser> user, GetChatRequest request) {
        Optional<MessengerUser> companion = userRepository.findByUsername(request.getUser());

        List<Message> messages = null;
        if(companion.isPresent()){
            MessengerUser u1 = user.get();
            MessengerUser u2 = companion.get();
            Chat chat = chatRepository.findChatByFirstUserAndSecondUser(u1, u2);

            if(chat != null){
                messages = messageRepository.findMessagesByChat(chat);
            }else{
                chat = chatRepository.findChatByFirstUserAndSecondUser(u2, u1);
                if(chat != null){
                    messages = messageRepository.findMessagesByChat(chat);
                }
            }
        }

        return messages;
    }


//    @Override
//    public List<Message> getNewMessages(Long receiverId) {
//        List<Message> receiverMessages = messageRepository.getMessagesByReceiver_Id(receiverId);
//        return receiverMessages.stream().filter(message -> message.isRead() == false).collect(Collectors.toList());
//    }

//    @Override
//    public List<Message> getChat(Long receiverId, Long senderId) {
//        return messageRepository.getMessagesByReceiver_IdAndSender_Id(receiverId, senderId);
//    }

    @Override
    public void updateAll(Iterable<Message> messages) {
        messageRepository.saveAll(messages);
    }

}
