package com.finalproject.server.controller;

import com.finalproject.server.entity.*;
import com.finalproject.server.mail.MailService;
import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.SendChangePasswordTokenRequest;
import com.finalproject.server.payload.response.ChangePasswordResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.security.token.CustomToken;
import com.finalproject.server.service.TokenOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ChangePasswordController {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final TokenOperations tokenOperations;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ChangePasswordController(UserRepository userRepository, MailService mailService, TokenOperations tokenOperations, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.tokenOperations = tokenOperations;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/sendTokenForChangingPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangePasswordToken(@RequestBody SendChangePasswordTokenRequest request) {
        String token = CustomToken.getToken();

        if (!userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else {
            Optional<MessengerUser> foundedUser = userRepository.findByUsername(request.getUsername());
            Token userToken = new Token();
            userToken.setValue(token);
            userToken.setMessengerUser(foundedUser.get());
            tokenOperations.add(userToken);
            mailService.sendSimpleMessage(
                    userRepository.findByUsername(request.getUsername()).get().getEmail(),
                    "Your personal token for changing password",
                    token
            );
            return ResponseEntity.ok(new ChangePasswordResponse(token, request.getUsername()));
        }

    }

    @PostMapping(value = "/submitChangingPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<MessengerUser> foundedUser = userRepository.findByUsername(request.getUsername());
        Token token = tokenOperations.findByValue(request.getToken());

        if (!userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: this username doesn't exist!"));
        } else if (tokenOperations.findByValueAndUser(token.getValue(), foundedUser.get()).isPresent()) {
            foundedUser.get().setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            userRepository.save(foundedUser.get());
            tokenOperations.deleteById(tokenOperations.findByValueAndUser(token.getValue(), foundedUser.get()).get().getId());
        }
        return ResponseEntity.ok("Password changed successful");
    }
}