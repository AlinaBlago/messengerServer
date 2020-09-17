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
    public ResponseEntity.BodyBuilder LoadUsersForAdmin(Long id, String key){

        Set<String> users = Collections.singleton(messageService.getUserChats(id).toString());

        userService.findAll().forEach(item -> {
            users.add(item.getLogin());
        });

        return ResponseEntity.status(HttpStatus.OK);
    }


}
