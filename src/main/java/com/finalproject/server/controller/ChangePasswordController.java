package com.finalproject.server.controller;

import com.finalproject.server.entity.*;
import com.finalproject.server.mail.MailService;
import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.SendChangePasswordTokenRequest;
import com.finalproject.server.payload.response.ChangePasswordResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.security.token.CustomToken;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ChangePasswordController {
    private final UserOperations userOperations;
    private final MailService mailService;
    private final TokenOperations tokenOperations;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ChangePasswordController(UserOperations userOperations, MailService mailService, TokenOperations tokenOperations, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userOperations = userOperations;
        this.mailService = mailService;
        this.tokenOperations = tokenOperations;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/sendTokenForChangingPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangePasswordToken(@RequestBody SendChangePasswordTokenRequest request) {
        String token = CustomToken.getToken();

        if (!userOperations.existByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else {
            Optional<User> foundedUser = userOperations.findByUsername(request.getUsername());
            Token userToken = new Token();
            userToken.setValue(token);
            userToken.setUser(foundedUser.get());
            tokenOperations.add(userToken);
            mailService.sendSimpleMessage(
                    userOperations.findByUsername(request.getUsername()).get().getEmail(),
                    "Your personal token for changing password",
                    token
            );
            return ResponseEntity.ok(new ChangePasswordResponse(token, request.getUsername()));
        }

    }

    @PostMapping(value = "/submitChangingPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<User> foundedUser = userOperations.findByUsername(request.getUsername());
        Token token = tokenOperations.findByValue(request.getToken());

        if (!userOperations.existByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else if (tokenOperations.findByValueAndUser(token.getValue(), foundedUser.get()).isPresent()) {
            foundedUser.get().setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            userOperations.update(foundedUser.get());
            tokenOperations.deleteById(tokenOperations.findByValueAndUser(token.getValue(), foundedUser.get()).get().getId());
        }
        return ResponseEntity.ok("Password changed successful");
    }
}