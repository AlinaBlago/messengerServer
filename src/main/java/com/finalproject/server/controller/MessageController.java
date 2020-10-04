package com.finalproject.server.controller;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.AddChatRequest;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.repository.ChatRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.UserOperations;
import com.finalproject.server.service.impl.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class MessageController {
    private final UserOperations userOperations;
    private final UserRepository userRepository;
    private final MessageOperations messageOperations;
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    public MessageController(UserOperations userOperations, UserRepository userRepository, MessageOperations messageOperations, ChatService chatService, ChatRepository chatRepository) {
        this.userOperations = userOperations;
        this.userRepository = userRepository;
        this.messageOperations = messageOperations;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }

    @PostMapping(value = "/me")
    public void sendMessage(@AuthenticationPrincipal String email, SendMessageRequest request){

    }

    @PostMapping(value = "/me/chats")
    public ResponseEntity<ChatResponse> addChat(@AuthenticationPrincipal String email, @RequestBody AddChatRequest request) {
        Optional<MessengerUser> user = userRepository.findByUsername(email);
        MessengerUser msgUser = user.get();
        Optional<MessengerUser> secondUser = userRepository.findByUsername(request.getUsername());

        if (secondUser.isPresent()) {
            var leftList = chatRepository.findChatsByFirstUser(msgUser);
            leftList.retainAll(chatRepository.findChatsBySecondUser(secondUser.get()));

            var swapLeftList = chatRepository.findChatsByFirstUser(secondUser.get());
            swapLeftList.retainAll(chatRepository.findChatsBySecondUser(msgUser));

            if (leftList.size() == 0 && swapLeftList.size() == 0)
            {
                Chat chat = new Chat();
                chat.setFirstUser(msgUser);
                chat.setSecondUser(secondUser.get());
                chatRepository.save(chat);
                ChatResponse resp = new ChatResponse(chat.getId(), chat.getFirstUser().getUsername(), chat.getSecondUser().getUsername());
                return ResponseEntity.ok(resp);
            }
        }
        return ResponseEntity.badRequest().body(null);
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
//
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
////
//    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity sendMessage(Long senderId, String login, Long receiverId, String message){
//
//        Optional<MessengerUser> sender = userOperations.findById(senderId);
//        Optional<MessengerUser> receiver = userOperations.findById(receiverId);
//
//        if(userOperations.findByUsername(login).isPresent())
//            if (!sender.isEmpty() && !receiver.isEmpty()) {
//            Message message1 = new Message(message, sender.get(), receiver.get(), new Date(System.currentTimeMillis()), false);
//            messageOperations.add(message1);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }
//
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
////
//    @RequestMapping(value = "/getChat" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity haveNewMessages(Long idSender, String senderLogin, Long idReceiver){
//
//        if(userOperations.findByUsername(senderLogin).isPresent()){
//
//            List<Message> messages = messageOperations.getChat(idSender, idReceiver);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }

}
