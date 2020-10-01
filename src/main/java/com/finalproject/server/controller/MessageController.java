package com.finalproject.server.controller;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import com.finalproject.server.payload.request.GetUserChatsRequest;
import com.finalproject.server.payload.request.LoginRequest;
import com.finalproject.server.security.jwt.JwtUtils;
import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class MessageController {
    private final UserOperations userOperations;
    private final MessageOperations messageOperations;

    public MessageController(UserOperations userOperations, MessageOperations messageOperations) {
        this.userOperations = userOperations;
        this.messageOperations = messageOperations;
    }

    @PostMapping(value = "/getUserChats")
    public ResponseEntity<?> getUserChats(@RequestParam GetUserChatsRequest request){
        JwtUtils utils = new JwtUtils();
        if(userOperations.findByUsername(utils.getUserNameFromJwtToken(request.getToken())).isPresent()) {
            Set<User> users = messageOperations.getUserChats(utils.getUserNameFromJwtToken(request.getToken()));

            return ResponseEntity.ok("Ok");
        } else return ResponseEntity.ok("Conflict");
    }

    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity isUserExists(String senderLogin, String userLogin){

        if (userOperations.findByUsername(senderLogin).isPresent())
            if(userOperations.existByUsername(userLogin)) {

               return new  ResponseEntity(HttpStatus.OK);
            }

        return new  ResponseEntity(HttpStatus.CONFLICT);
    }
//
    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendMessage(Long senderId, String login, Long receiverId, String message){

        Optional<User> sender = userOperations.findById(senderId);
        Optional<User> receiver = userOperations.findById(receiverId);

        if(userOperations.findByUsername(login).isPresent())
            if (!sender.isEmpty() && !receiver.isEmpty()) {
            Message message1 = new Message(message, sender.get(), receiver.get(), new Date(System.currentTimeMillis()), false);
            messageOperations.add(message1);

            return new  ResponseEntity(HttpStatus.OK);
        }

        return new  ResponseEntity(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/haveNewMessages" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity haveNewMessages(Long senderId, String login){

        if(userOperations.findByUsername(login).isPresent()) {
            List<Message> messages = messageOperations.getNewMessages(senderId);

            return new  ResponseEntity(HttpStatus.OK);
        }

        return new  ResponseEntity(HttpStatus.CONFLICT);
    }
//
    @RequestMapping(value = "/getChat" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity haveNewMessages(Long idSender, String senderLogin, Long idReceiver){

        if(userOperations.findByUsername(senderLogin).isPresent()){

            List<Message> messages = messageOperations.getChat(idSender, idReceiver);

            return new  ResponseEntity(HttpStatus.OK);
        }

        return new  ResponseEntity(HttpStatus.CONFLICT);
    }

}
