package com.finalproject.server.controller;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.response.FindUserResponse;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/admins")
public class AdminController {
    private final UserOperations userOperations;
    private final UserRepository userRepository;

    public AdminController(UserOperations userOperations, UserRepository userRepository) {
        this.userOperations = userOperations;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SignupRequest request) {
        return userOperations.createAdmin(request);
    }

    @PostMapping(value = "/chats")
    public ResponseEntity<UserResponse> getUserByUsername(@RequestBody UserRequest request) {
        MessengerUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> MessengerExceptions.userNotFound(request.getUsername()));
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setCreatedAt(user.getCreatedAt());
        response.setEmail(user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/find")
    public FindUserResponse findUser(@RequestBody UserRequest request){
        List<MessengerUser> users = userRepository.findMessengerUsersByUsernameIsStartingWith(request.getUsername());
        ArrayList<String> usernames = new ArrayList<>();
        users.forEach(user -> usernames.add(user.getUsername()));

        return new FindUserResponse(usernames);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteUser(@RequestBody UserRequest request) {
        userOperations.deleteByUsername(request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> lockUser(@RequestBody UserRequest request) {
        userOperations.lockByUsername(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> unLockCurrentUser(@RequestBody UserRequest request) {
        userOperations.unLockByUsername(request);
        return ResponseEntity.ok().build();
    }
}
