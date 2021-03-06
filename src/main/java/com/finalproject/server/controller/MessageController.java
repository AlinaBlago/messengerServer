package com.finalproject.server.controller;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.ChatOperations;
import com.finalproject.server.service.MessageOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MessageController {
    private final UserRepository userRepository;
    private final MessageOperations messageOperations;
    private final ChatOperations chatOperations;

    public MessageController(UserRepository userRepository, MessageOperations messageOperations, ChatOperations chatOperations) {
        this.userRepository = userRepository;
        this.messageOperations = messageOperations;
        this.chatOperations = chatOperations;
    }

    @PostMapping(value = "/me/chats")
    public ResponseEntity<ChatResponse> addChat(@AuthenticationPrincipal String email, @RequestBody UserRequest request) {
        ChatResponse response =  chatOperations.addChat(email, request);
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
        MessengerUser msgUser = user.orElseThrow(() -> MessengerExceptions.userNotFound(email));
        chatOperations.findChats(msgUser).forEach(chat -> {
            ChatResponse resp = new ChatResponse(chat.getId() , chat.getFirstUser().getUsername(), chat.getSecondUser().getUsername());
            response.add(resp);
        });

        return response;
    }

    @PostMapping(value = "/me/messages")
    public MessageResponse sendMessage(@AuthenticationPrincipal String email, @RequestBody SendMessageRequest request) {
        Message message = messageOperations.save(userRepository.findByUsername(email).orElseThrow(() -> MessengerExceptions.userNotFound(email)), request);

        return new MessageResponse(message);
    }

    @PostMapping(value = "/me/chat")
    public ResponseEntity<List<MessageResponse>> getChat(@AuthenticationPrincipal String email, @RequestBody UserRequest request){
        List<Message> messages = messageOperations.loadChat(userRepository.findByUsername(email)
                .orElseThrow(() -> MessengerExceptions.userNotFound(email)), request);

        if (messages != null){
            List<MessageResponse> msgResp = new ArrayList<>();
            messages.forEach(msg -> msgResp.add(new MessageResponse(msg)));
            return ResponseEntity.ok(msgResp);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/me/newMessages")
    public SseEmitter loadMessages(@AuthenticationPrincipal String email) {
        MessengerUser user = userRepository.findByUsername(email).orElseThrow(() -> MessengerExceptions.userNotFound(email));

        return messageOperations.loadMessages(user);
    }

}
