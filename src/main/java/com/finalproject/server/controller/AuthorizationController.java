package com.finalproject.server.controller;

import com.finalproject.server.entity.Role;
import com.finalproject.server.entity.User;
import com.finalproject.server.service.RoleOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.stream.Collectors;

@RestController
public class AuthorizationController {

    private final UserOperations userOperations;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleOperations roleOperations;

    public AuthorizationController(UserOperations userOperations, BCryptPasswordEncoder bCryptPasswordEncoder, RoleOperations roleOperations) {
        this.userOperations = userOperations;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleOperations = roleOperations;
    }

    @RequestMapping(value = "/signUp" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity.BodyBuilder signUp(String name, String login, String password){

        if(userOperations.findByPasswordAndLogin(password, login) == null){
            User user = new User();
            user.setName(name);
            //user.setRoles(roleOperations.findById(1L).stream().collect(Collectors.toSet()));
            //user.setRoles(Collections.singleton(new Role((1L), "USER_ROLE")));
            user.setRoles(roleOperations.findById(1L).stream().collect(Collectors.toSet()));
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setUsername(login);
            user.setBanned(false);
            user.setEnabled(true);
            userOperations.add(user);
            userOperations.save(user);
            userOperations.update(user);

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
