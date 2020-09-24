package com.finalproject.server.controller;

import com.finalproject.server.entity.User;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorizationController {

    private final UserOperations userOperations;

    public AuthorizationController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @RequestMapping(value = "/signUp" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder signUp(String name, String login, String password){

        if(userOperations.loadUserByUsername(login) == null){
            User user = new User(name, login, password, false, true);
            userOperations.add(user);
            userOperations.save(user);

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.OK);
    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> login(String login, String password) {
        User foundedUser = userOperations.findByPasswordAndLogin(login, password);

        if (foundedUser == null) {
            return new ResponseEntity<Integer>(0, HttpStatus.OK);
        }

        if (foundedUser.getName().length() == 0 && foundedUser.getUsername().length() == 0 && foundedUser.getPassword().length() == 0) {
            return new ResponseEntity<Integer>(0, HttpStatus.OK);
        }

           return new ResponseEntity<Integer>(0, HttpStatus.OK);

    }

}
