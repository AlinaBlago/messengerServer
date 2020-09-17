package com.finalproject.server.controller;

import com.finalproject.server.service.impl.MessageServiceImpl;
import com.finalproject.server.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Set;

@RestController
public class AdminController {
    MessageServiceImpl messageService = new MessageServiceImpl();
    UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping(value = "/LoadUsersForAdmin" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder loadUsersForAdmin(Long id, String key){

        Set<String> users = Collections.singleton(messageService.getUserChats(id).toString());

        userService.findAll().forEach(item -> {
            users.add(item.getLogin());
        });

        return ResponseEntity.status(HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder deleteUser(Long id , String key){

        if(userService.isUserHaveAccess(id, key)){
            userService.deleteUserById(id);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/banUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder banUser(Long id, String key, Long userToBanId){

        if(userService.isUserHaveAccess(id, key)){
            userService.banUser(userToBanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/unBanUser" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder unbanUser(Long id, String key, Long userToUnbanId){

        if(userService.isUserHaveAccess(id, key)){
            userService.unBanUser(userToUnbanId);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }
}
