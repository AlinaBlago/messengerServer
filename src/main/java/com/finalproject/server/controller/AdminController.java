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

    @RequestMapping(value = "/LoadUsersForAdmin" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder loadUsersForAdmin(Long id, String key){

        Set<String> users = Collections.singleton(messageOperations.getUserChats(id).toString());

        userOperations.findAll().forEach(item -> {
            users.add(item.getLogin());
        });

        return ResponseEntity.status(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder deleteUser(Long id , String key){

        if(userOperations.isUserHaveAccess(id, key)){
            userOperations.deleteUserById(id);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/banUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder banUser(Long id, String key, Long userToBanId){

        if(userOperations.isUserHaveAccess(id, key)){
            userOperations.banUser(userToBanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/unBanUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder unBanUser(Long id, String key, Long userToUnBanId){

        if(userOperations.isUserHaveAccess(id, key)){
            userOperations.unBanUser(userToUnBanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }
}
