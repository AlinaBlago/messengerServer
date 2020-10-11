package com.finalproject.server.service.impl;


import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.ChatRepository;
import com.finalproject.server.repository.MessageRepository;
import com.finalproject.server.repository.UserRepository;
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
    public List<Message> loadChat(Optional<MessengerUser> user, UserRequest request) {
        Optional<MessengerUser> companion = userRepository.findByUsername(request.getUsername());

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

    @Override
    public List<MessageResponse> getNewMessages(Chat chat , MessengerUser receiver ){
        List<Message> messages = messageRepository.findMessagesByChat(chat);
        messages = messages.stream().filter(msg -> {
            return msg.isReceived() == false &&  !msg.getSender().getUsername().equals(receiver.getUsername());
        }).collect(Collectors.toList());

        try{
            List<MessageResponse> msgResp = new ArrayList<>();
            messages.forEach(msg -> {
                msgResp.add(new MessageResponse(msg)) ;
            });
            return msgResp;
        }
        finally {
            messages.forEach(msg -> {
                msg.setReceived(true);
                messageRepository.save(msg);
            });
        }
    }

//    @Override
//    public List<Message> getChat(Long receiverId, Long senderId) {
//        return messageRepository.getMessagesByReceiver_IdAndSender_Id(receiverId, senderId);
//    }

    @Override
    public void updateAll(Iterable<Message> messages) {
        messageRepository.saveAll(messages);
    }

}
