package com.finalproject.server.controller;

import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.request.UpdateUserRequest;
import com.finalproject.server.payload.response.UserResponse;

import com.finalproject.server.security.properties.SecurityProperties;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthorizationController {

    private final UserOperations userOperations;
    private final SecurityProperties securityProperties;


    public AuthorizationController(UserOperations userOperations, SecurityProperties securityProperties) {
        this.userOperations = userOperations;
        this.securityProperties = securityProperties;
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
        return userOperations.findByEmail(email).orElseThrow(() -> MessengerExceptions.userNotFound(email));
    }

    @PatchMapping("/me")
    public UserResponse mergeCurrentUser(@AuthenticationPrincipal String email,
                                         @RequestBody @Valid UpdateUserRequest request) {
        return userOperations.updateByEmail(email, request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@AuthenticationPrincipal String email) {
        userOperations.deleteByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id) {
        return userOperations.findById(id).orElseThrow(() -> MessengerExceptions.userNotFound(id));
    }

}



