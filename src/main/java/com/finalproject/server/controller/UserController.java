package com.finalproject.server.controller;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.*;
import com.finalproject.server.payload.response.UserResponse;

import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.security.properties.SecurityProperties;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import com.finalproject.server.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest request) {
//        Set<String> strStates = request.getState();
//        Set<State> states = new HashSet<>();
//        if (strStates == null) {
//            State userState = stateOperations.findByName(EState.ACTIVE)
//                    .orElseThrow(() -> new RuntimeException("Error: State is not found."));
//            states.add(userState);
//        } else {
//            strStates.forEach(state -> {
//                switch (state) {
//                    case "locked":
//                        State lockedState = stateOperations.findByName(EState.LOCKED)
//                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
//                        states.add(lockedState);
//                        break;
//                    case "deleted":
//                        State deletedState = stateOperations.findByName(EState.DELETED)
//                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
//                        states.add(deletedState);
//                        break;
//                    default:
//                        State activeState = stateOperations.findByName(EState.ACTIVE)
//                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
//                        states.add(activeState);
//                }
//            });
//        }
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid SignupRequest request) {
        return userOperations.create(request);
    }


    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SignupRequest request) {
        return userOperations.createAdmin(request);
    }


    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal String email) {
        return userOperations.findByUsername(email).orElseThrow(() -> MessengerExceptions.userNotFound(email));
    }

    //TODO
    @PatchMapping("/me")
    public UserResponse mergeCurrentUser(@AuthenticationPrincipal String email,
                                         @RequestBody @Valid UpdateUserRequest request) {
        return userOperations.updateByUsername(email, request);
    }

    //TODO
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@AuthenticationPrincipal String email) {
        userOperations.deleteByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id) {
        return userOperations.findById(id).orElseThrow(() -> MessengerExceptions.userNotFound(id));
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

}



