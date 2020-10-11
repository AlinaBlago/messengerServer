package com.finalproject.server.controller;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.ChatRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.impl.ChatService;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/users")
public class MessageController {
    private final UserRepository userRepository;
    private final MessageOperations messageOperations;
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    public MessageController(UserRepository userRepository, MessageOperations messageOperations, ChatService chatService, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.messageOperations = messageOperations;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }

    @PostMapping(value = "/me/chats")
    public ResponseEntity<ChatResponse> addChat(@AuthenticationPrincipal String email, @RequestBody UserRequest request) {
        ChatResponse response =  chatService.addChat(userRepository.findByUsername(email), request);
        if (response != null){
            return ResponseEntity.ok(response);
            } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/me/chats")
    public List<ChatResponse> getUserChats(@AuthenticationPrincipal String email){
        List<ChatResponse> response = new ArrayList<>();
        Optional<MessengerUser> user = userRepository.findByUsername(email);
        MessengerUser msgUser = user.get();
        chatService.findChats(msgUser).forEach(chat -> {
            ChatResponse resp = new ChatResponse(chat.getId() , chat.getFirstUser().getUsername(), chat.getSecondUser().getUsername());
            response.add(resp);
        });

        return response;
    }

    @PostMapping(value = "/me/messages")
    public MessageResponse sendMessage(@AuthenticationPrincipal String email, @RequestBody SendMessageRequest request) {
        Message message = messageOperations.add(userRepository.findByUsername(email), request);

        MessageResponse resp = new MessageResponse(message);

        return resp;
    }

    @PostMapping(value = "/me/chat")
    public ResponseEntity<List<MessageResponse>> getChat(@AuthenticationPrincipal String email, @RequestBody UserRequest request){
        List<Message> messages = messageOperations.loadChat(userRepository.findByUsername(email), request);

        if (messages != null){
            List<MessageResponse> msgResp = new ArrayList<>();
            messages.forEach(msg -> {
                msgResp.add(new MessageResponse(msg));
            });
            return ResponseEntity.ok(msgResp);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/me/newMessages")
    public SseEmitter streamSseMvc(@AuthenticationPrincipal String email) {
        System.out.println("Connected " + email);
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        Optional<MessengerUser> user = userRepository.findByUsername(email);
        List<Chat> chats = chatService.findChats(user.get());

        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            Thread t1 = new Thread(()->{
                Gson gson = new Gson();
                for (int i = 0; true ; i++) {
                    chats.forEach(chat -> {
                        try {
                            List<MessageResponse> messages = messageOperations.getNewMessages(chat , user.get());

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
