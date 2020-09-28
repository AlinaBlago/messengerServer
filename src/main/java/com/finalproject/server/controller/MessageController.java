package com.finalproject.server.controller;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MessageController {
//    private final UserOperations userOperations;
//    private final MessageOperations messageOperations;
//
//    public MessageController(UserOperations userOperations, MessageOperations messageOperations) {
//        this.userOperations = userOperations;
//        this.messageOperations = messageOperations;
//    }
//
//    @RequestMapping(value = "/getUserChats" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getUserChats(String login){
//
//        if(userOperations.loadUserByUsername(login) != null){
//            Set<User> users = messageOperations.getUserChats(login);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }
//
//    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity isUserExists(String senderLogin, String userLogin){
//
//        if (userOperations.loadUserByUsername(senderLogin) != null)
//            if(userOperations.existByUsername(userLogin)) {
//
//               return new  ResponseEntity(HttpStatus.OK);
//            }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }
//
//    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity sendMessage(Long senderId, String login, Long receiverId, String message){
//
//        Optional<User> sender = userOperations.findById(senderId);
//        Optional<User> receiver = userOperations.findById(receiverId);
//
//        if(userOperations.loadUserByUsername(login) != null)
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
//        if(userOperations.loadUserByUsername(login) != null) {
//            List<Message> messages = messageOperations.getNewMessages(senderId);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }
//
//    @RequestMapping(value = "/getChat" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity haveNewMessages(Long idSender, String senderLogin, Long idReceiver){
//
//        if(userOperations.loadUserByUsername(senderLogin) != null){
//
//            List<Message> messages = messageOperations.getChat(idSender, idReceiver);
//
//            return new  ResponseEntity(HttpStatus.OK);
//        }
//
//        return new  ResponseEntity(HttpStatus.CONFLICT);
//    }

}
