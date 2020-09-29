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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class ChangePasswordController {
    private final UserOperations userOperations;
    private final MailService mailService;
    private final TokenOperations tokenOperations;

    public ChangePasswordController(UserOperations userOperations, MailService mailService, TokenOperations tokenOperations) {
        this.userOperations = userOperations;
        this.mailService = mailService;
        this.tokenOperations = tokenOperations;
    }

    @PostMapping(value = "/sendTokenForChangingPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangePasswordToken(@RequestBody SendChangePasswordTokenRequest request) {
        String token = CustomToken.getToken();
        Set<Token> tokens = new HashSet<>();

        if (!userOperations.existByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else {
            Token userToken = tokenOperations.findByName(EToken.CHANGE_PASSWORD_TOKEN)
                    .orElseThrow(() -> new RuntimeException("Error: Token is not found."));
            userToken.setValue(token);
            tokens.add(userToken);

            Optional<User> foundedUser = userOperations.findByUsername(request.getUsername());
            foundedUser.get().setTokens(tokens);
            userOperations.save(foundedUser.get());

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

        if (!userOperations.existByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else {
            Token userToken = tokenOperations.findByNameAndValue(EToken.CHANGE_PASSWORD_TOKEN, request.getToken())
                    .orElseThrow(() -> new RuntimeException("Error: Token is not found."));

            foundedUser.get().setPassword(request.getPassword());
            userOperations.update(foundedUser.get());

            return ResponseEntity.ok(" ");
        }

    }
}
