package com.finalproject.server.controller;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.*;
import com.finalproject.server.payload.response.FindUserResponse;
import com.finalproject.server.payload.response.UserResponse;

import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import com.finalproject.server.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserOperations userOperations;
    private final UserRepository userRepository;
    private final TokenOperations tokenOperations;
    private final UserService userService;

    public UserController(UserOperations userOperations, UserRepository userRepository, TokenOperations tokenOperations, UserService userService) {
        this.userOperations = userOperations;
        this.userRepository = userRepository;
        this.tokenOperations = tokenOperations;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid SignupRequest request) {
        return userOperations.create(request);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal String email) {
        return userOperations.findByUsername(email).orElseThrow(() -> MessengerExceptions.userNotFound(email));
    }

    @PatchMapping("/me/login")
    public UserResponse updateUsername(@AuthenticationPrincipal String email,
                                         @RequestBody @Valid UpdateUserLoginRequest request) {
        return userOperations.updateUsername(email, request);
    }

    @PatchMapping("/me/password")
    public UserResponse updatePassword(@AuthenticationPrincipal String email,
                                         @RequestBody @Valid UpdateUserPasswordRequest request) {
        return userOperations.updatePassword(email, request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@AuthenticationPrincipal String email) {
       userOperations.deleteByUsername(email);
    }

    @PostMapping(value = "/me/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendChangeEmailToken(@AuthenticationPrincipal String email, @RequestBody GetTokenForUpdateEmailRequest request) {
        MessengerUser user = userRepository.findByUsername(email).get();
        String token = tokenOperations.add(user ,request);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/me/email/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangeEmailRequest request) {
        MessengerUser user = userRepository.findByUsername(email).get();
        userService.updateEmail(user, request);
        return ResponseEntity.ok("Email changed successful");
    }

    @PostMapping(value = "/find")
    public FindUserResponse findUser(@RequestBody UserRequest request){

        List<MessengerUser> users = userRepository.findMessengerUsersByUsernameIsStartingWith(request.getUsername());

        ArrayList<String> usernames = new ArrayList<>();
        users.forEach(user ->{
            usernames.add(user.getUsername());
        });

        return new FindUserResponse(usernames);
    }
}



