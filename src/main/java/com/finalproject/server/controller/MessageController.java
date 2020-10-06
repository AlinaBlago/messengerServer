package com.finalproject.server.controller;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.AddChatRequest;
import com.finalproject.server.payload.request.GetChatRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.impl.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MessageController {
    private final UserRepository userRepository;
    private final MessageOperations messageOperations;
    private final ChatService chatService;

    public MessageController(UserRepository userRepository, MessageOperations messageOperations, ChatService chatService) {
        this.userRepository = userRepository;
        this.messageOperations = messageOperations;
        this.chatService = chatService;
    }

    @PostMapping(value = "/me/chats")
    public ResponseEntity<ChatResponse> addChat(@AuthenticationPrincipal String email, @RequestBody AddChatRequest request) {
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
    public ResponseEntity sendMessage(@AuthenticationPrincipal String email, @RequestBody SendMessageRequest request) {
        Message message = messageOperations.add(userRepository.findByUsername(email), request);

        if (message == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok("Ok");
        }
    }

    @PostMapping(value = "/me/chat")
    public ResponseEntity<List<MessageResponse>> getChat(@AuthenticationPrincipal String email, @RequestBody GetChatRequest request){
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

//    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity isUserExists(String senderLogin, String userLogin){
//
//        if (userOperations.findByUsername(senderLogin).isPresent())
//            if(userOperations.existByUsername(userLogin)) {
//
//               return new  ResponseEntity(HttpStatus.OK);
//            }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }

//    @RequestMapping(value = "/haveNewMessages" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity haveNewMessages(Long senderId, String login){
//
//        if(userOperations.findByUsername(login).isPresent()) {
//            List<Message> messages = messageOperations.getNewMessages(senderId);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }


}
