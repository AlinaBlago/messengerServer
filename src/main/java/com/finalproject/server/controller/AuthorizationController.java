package com.finalproject.server.controller;

import com.finalproject.server.custom_class.MurmurHash;
import com.finalproject.server.entity.User;
import com.finalproject.server.service.KeyOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private final UserOperations userOperations;
    private final KeyOperations keyOperations;

    public AuthorizationController(UserOperations userOperations, KeyOperations keyOperations) {
        this.userOperations = userOperations;
        this.keyOperations = keyOperations;
    }

    @RequestMapping(value = "/signUp" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder signUp(String name, String login, String password){

        if(userOperations.findUserByLogin(login) == null){
            User user = new User(name, login, password, false, false);
            userOperations.add(user);
            userOperations.save(user);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder login(String login, String password){

        User foundedUser = userOperations.findUserByLoginAndPassword(login, password);
        if(foundedUser.getName().length() == 0 && foundedUser.getLogin().length() == 0 && foundedUser.getPassword().length() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        int userKey = MurmurHash.hash32((login + System.currentTimeMillis()));

        keyOperations.registerNewSignUp(foundedUser, Integer.toString(userKey));

        return ResponseEntity.status(HttpStatus.OK);
    }

}
