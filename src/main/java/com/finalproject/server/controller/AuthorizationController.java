package com.finalproject.server.controller;

import com.finalproject.server.entity.User;
import com.finalproject.server.payload.request.LoginRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.service.RoleOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp(@Valid @RequestBody SignupRequest request) {
        if (userOperations.findByLogin(request.getLogin()) == null) {
            User user = new User();
            user.setName(request.getName());
            user.setRoles(roleOperations.findById(1L).stream().collect(Collectors.toSet()));
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            user.setUsername(request.getLogin());
            user.setBanned(false);
            user.setEnabled(true);
            userOperations.add(user);
            userOperations.save(user);
            userOperations.update(user);
            return new ResponseEntity(HttpStatus.CREATED);
        } else return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@Valid @RequestBody LoginRequest request) {
        if (bCryptPasswordEncoder.matches(request.getPassword(), userOperations.findByLogin(request.getLogin()).getPassword())) {
            return ResponseEntity.ok("User is logined on");
        }
            return new ResponseEntity(HttpStatus.CONFLICT);
    }
}

