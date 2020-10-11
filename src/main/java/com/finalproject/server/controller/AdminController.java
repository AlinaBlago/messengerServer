package com.finalproject.server.controller;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import com.finalproject.server.service.impl.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final UserOperations userOperations;
    private final UserRepository userRepository;

    public AdminController(UserOperations userOperations, UserRepository userRepository) {
        this.userOperations = userOperations;
        this.userRepository = userRepository;
    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SignupRequest request) {
        return userOperations.createAdmin(request);
    }

    @PostMapping(value = "/admins/chats")
    public ResponseEntity<UserResponse> getUserByUsername(@RequestBody UserRequest request) {
        MessengerUser user = userRepository.findByUsername(request.getUsername()).get();
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setCreatedAt(user.getCreatedAt());
        response.setEmail(user.getEmail());

        if (response != null){
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/admins")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteUser(@RequestBody UserRequest request) {
        userOperations.deleteByUsername(request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/admins")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity lockUser(@RequestBody UserRequest request) {
        userOperations.lockByUsername(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admins/unlock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity unLockCurrentUser(@RequestBody UserRequest request) {
        userOperations.unLockByUsername(request);
        return ResponseEntity.ok().build();
    }
}
