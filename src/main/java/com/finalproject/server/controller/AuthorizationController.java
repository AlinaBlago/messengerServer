package com.finalproject.server.controller;

import com.finalproject.server.customClass.MurmurHash;
import com.finalproject.server.entity.User;
import com.finalproject.server.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping(value = "/signUp" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder signUp(String name, String login, String password){

        if(userService.findUserByLogin(login).isEmpty()){
            userService.add(new User(name, login, password, false, false));

            return ResponseEntity.status(HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/login" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder login(String login, String password){

        User foundedUser = userService.findUserByLoginAndPassword(login, password);
        if(foundedUser.getName().length() == 0 && foundedUser.getLogin().length() == 0 && foundedUser.getPassword().length() == 0){

            return ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        int userKey = MurmurHash.hash32((login + System.currentTimeMillis()));

        //TODO
//        Users.keys.put(login , userKey);
//        response.setResponseMessage(Integer.toString(userKey));

        return ResponseEntity.status(HttpStatus.OK);
    }

}
