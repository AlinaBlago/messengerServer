package com.finalproject.server.controller;

import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.ChangePasswordResponse;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {
    private final UserOperations userOperations;
    private final TokenOperations tokenOperations;

    public ChangePasswordController(UserOperations userOperations, TokenOperations tokenOperations) {
        this.userOperations = userOperations;
        this.tokenOperations = tokenOperations;
    }

    @PostMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangePasswordToken(@RequestBody UserRequest request) {
        String token = tokenOperations.add(request);
        return ResponseEntity.ok(new ChangePasswordResponse(token, request.getUsername()));
    }

    @PostMapping(value = "/password/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        userOperations.updateForgottenPassword(request);
        return ResponseEntity.ok("Password changed successful");
    }
}