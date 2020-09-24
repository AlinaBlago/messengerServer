package com.finalproject.server.controller;

import com.finalproject.server.service.MessageOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
public class AdminController {

    private final MessageOperations messageOperations;
    private final UserOperations userOperations;

    public AdminController(MessageOperations messageOperations, UserOperations userOperations) {
        this.messageOperations = messageOperations;
        this.userOperations = userOperations;
    }

    @RequestMapping(value = "/loadUsersForAdmin" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> loadUsersForAdmin(String login){

        Set<String> users = Collections.singleton(messageOperations.getUserChats(login).toString());

        userOperations.findAll().forEach(item -> {
            users.add(item.getUsername());
        });

        return new ResponseEntity<Set<String>>(users , HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder deleteUser(Long id, String adminLogin){

        if(userOperations.loadUserByUsername(adminLogin) != null){
            userOperations.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/banUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder banUser(String adminLogin, Long userToBanId){

        if(userOperations.loadUserByUsername(adminLogin) != null){
            userOperations.ban(userToBanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/unBanUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder unBanUser(String adminLogin, Long userToUnBanId){

        if(userOperations.loadUserByUsername(adminLogin) != null){
            userOperations.unBan(userToUnBanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }
}
