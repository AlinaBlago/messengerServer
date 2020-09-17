package com.finalproject.server.controller;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import com.finalproject.server.service.impl.MessageServiceImpl;
import com.finalproject.server.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MessageController {
    UserServiceImpl userService = new UserServiceImpl();
    MessageServiceImpl messageService = new MessageServiceImpl();

    @RequestMapping(value = "/getUserChats" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder getUserChats(Long id, String key){

        if(userService.isUserHaveAccess(id, key)){
            Set<User> users = messageService.getUserChats(id);

//            Gson gson = new Gson();
//            String usersInString = gson.toJson(users);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/isUserExists" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder isUserExists(Long senderId, String senderKey, String userLogin){

        if(userService.isUserHaveAccess(senderId, senderKey)){
            if(userService.isExistByLogin(userLogin)) {

               return ResponseEntity.status(HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/sendMessage" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder sendMessage(Long senderId, String senderKey, Long receiverId, String message){

        Optional<User> sender = userService.findUserById(senderId);
        Optional<User> receiver = userService.findUserById(receiverId);

        if(userService.isUserHaveAccess(senderId, senderKey) && !sender.isEmpty() && !receiver.isEmpty()){
            Message message1 = new Message(message, sender.get(), receiver.get(), new Date(System.currentTimeMillis()), false);
            messageService.add(message1);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/haveNewMessages" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder haveNewMessages(Long senderId, String senderKey){

        if(userService.isUserHaveAccess(senderId, senderKey)){
            List<Message> messages = messageService.getNewMessagesByReceiverAndReadFalse(senderId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/getChat" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder haveNewMessages(Long idSender, String senderKey, Long idReceiver){

        if(userService.isUserHaveAccess(idSender, senderKey)){

            List<Message> messages = messageService.getChat(idSender, idReceiver);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

}
