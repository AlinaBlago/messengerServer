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
    private final UserOperations userOperations;
    private final MessageOperations messageOperations;

    public MessageController(UserOperations userOperations, MessageOperations messageOperations) {
        this.userOperations = userOperations;
        this.messageOperations = messageOperations;
    }

    @RequestMapping(value = "/getUserChats" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder getUserChats(Long id, String key){

        if(userOperations.isUserHaveAccess(id, key)){
            Set<User> users = messageOperations.getUserChats(id);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder isUserExists(Long senderId, String senderKey, String userLogin){

        if(userOperations.isUserHaveAccess(senderId, senderKey)){
            if(userOperations.isExistByLogin(userLogin)) {

               return ResponseEntity.status(HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder sendMessage(Long senderId, String senderKey, Long receiverId, String message){

        Optional<User> sender = userOperations.findUserById(senderId);
        Optional<User> receiver = userOperations.findUserById(receiverId);

        if(userOperations.isUserHaveAccess(senderId, senderKey) && !sender.isEmpty() && !receiver.isEmpty()){
            Message message1 = new Message(message, sender.get(), receiver.get(), new Date(System.currentTimeMillis()), false);
            messageOperations.add(message1);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/haveNewMessages" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder haveNewMessages(Long senderId, String senderKey){

        if(userOperations.isUserHaveAccess(senderId, senderKey)){
            List<Message> messages = messageOperations.getNewMessagesByReceiverAndReadFalse(senderId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/getChat" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder haveNewMessages(Long idSender, String senderKey, Long idReceiver){

        if(userOperations.isUserHaveAccess(idSender, senderKey)){

            List<Message> messages = messageOperations.getChat(idSender, idReceiver);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

}
