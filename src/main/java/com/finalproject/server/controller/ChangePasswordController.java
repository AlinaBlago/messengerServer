package com.finalproject.server.controller;

import com.finalproject.server.entity.*;
import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.SendChangePasswordTokenRequest;
import com.finalproject.server.payload.response.ChangePasswordResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import com.finalproject.server.service.impl.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ChangePasswordController {
    private final UserService userService;
    private final TokenOperations tokenOperations;

    public ChangePasswordController(UserService userService, TokenOperations tokenOperations) {
        this.userService = userService;
        this.tokenOperations = tokenOperations;
    }

    @PostMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangePasswordToken(@RequestBody SendChangePasswordTokenRequest request) {
        String token = tokenOperations.add(request);
        return ResponseEntity.ok(new ChangePasswordResponse(token, request.getUsername()));
    }

    @PostMapping(value = "/password/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.update(request);
        return ResponseEntity.ok("Password changed successful");
    }
}