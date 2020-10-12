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
import com.finalproject.server.service.ChatOperations;
import com.finalproject.server.service.MessageOperations;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService implements MessageOperations {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatOperations chatOperations;


    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChatOperations chatOperations) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatOperations = chatOperations;
    }

    @Override
    public Message save(MessengerUser sender, SendMessageRequest request) {
        Optional<MessengerUser> receiver = userRepository.findByUsername(request.getReceiver());
        Message message = new Message();

        if(receiver.isPresent()) {
            Chat chat = chatOperations.findChat(sender, receiver.get());
            if(chat != null){
                message.setBody(request.getMessage());
                message.setSender(sender);
                message.setDate(new Date(System.currentTimeMillis()));
                message.setChat(chat);
                message.setRead(false);
                messageRepository.save(message);
            } else{
                chat = chatOperations.findChat(receiver.get(), sender);
                if(chat != null){
                    message.setBody(request.getMessage());
                    message.setSender(sender);
                    message.setDate(new Date(System.currentTimeMillis()));
                    message.setChat(chat);
                    message.setRead(false);
                    messageRepository.save(message);
                }
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
            Chat chat = chatOperations.findChat(u1, u2);
         //   Chat chat = chatRepository.findChatByFirstUserAndSecondUser(u1, u2);

            if(chat != null){
                messages = messageRepository.findMessagesByChat(chat);
            }else{
                chat = chatOperations.findChat(u2, u1);
                if(chat != null){
                    messages = messageRepository.findMessagesByChat(chat);
                }
            }
        }

        return messages;
    }

    @Override
    public List<MessageResponse> getNewMessages(Chat chat, MessengerUser receiver ){
        List<Message> messages = messageRepository.findMessagesByChat(chat);
        messages = messages.stream().filter(msg -> !msg.isReceived() &&
                !msg.getSender().getUsername().equals(receiver.getUsername())).collect(Collectors.toList());

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

    @Override
    public SseEmitter loadMessages(MessengerUser user) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        List<Chat> chats = chatOperations.findChats(user);

        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            Thread t1 = new Thread(()->{
                Gson gson = new Gson();
                for (int i = 0; true ; i++) {
                    chats.forEach(chat -> {
                        try {
                            List<MessageResponse> messages = getNewMessages(chat, user);

                            if(!messages.isEmpty()) {
                                String jsonReq = gson.toJson(messages);
                                SseEmitter.SseEventBuilder event = SseEmitter.event()
                                        .data(jsonReq);

                                emitter.send(event);
                            }
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            emitter.completeWithError(e);
                        }
                    });

                }
            });
            t1.start();

        });
        sseMvcExecutor.shutdown();
        return emitter;
    }
}
