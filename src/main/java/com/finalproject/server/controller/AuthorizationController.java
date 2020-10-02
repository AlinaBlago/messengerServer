package com.finalproject.server.controller;

import com.finalproject.server.entity.*;
import com.finalproject.server.payload.request.LoginRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.security.UserDetailsImpl;
import com.finalproject.server.security.jwt.JwtUtils;
import com.finalproject.server.service.RoleOperations;
import com.finalproject.server.service.StateOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/auth")
public class AuthorizationController {

    private final UserOperations userOperations;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleOperations roleOperations;
    private final StateOperations stateOperations;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthorizationController(UserOperations userOperations, BCryptPasswordEncoder bCryptPasswordEncoder, RoleOperations roleOperations, StateOperations stateOperations, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userOperations = userOperations;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleOperations = roleOperations;
        this.stateOperations = stateOperations;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest request) {
        if (userOperations.existByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userOperations.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        MessengerUser messengerUser = new MessengerUser(
                request.getEmail(),
                request.getUsername(),
                bCryptPasswordEncoder.encode(request.getPassword())
                );

        Set<String> strStates = request.getState();
        Set<State> states = new HashSet<>();
        if (strStates == null) {
            State userState = stateOperations.findByName(EState.ACTIVE)
                    .orElseThrow(() -> new RuntimeException("Error: State is not found."));
            states.add(userState);
        } else {
            strStates.forEach(state -> {
                switch (state) {
                    case "locked":
                        State lockedState = stateOperations.findByName(EState.LOCKED)
                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
                        states.add(lockedState);
                        break;
                    case "deleted":
                        State deletedState = stateOperations.findByName(EState.DELETED)
                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
                        states.add(deletedState);
                        break;
                    default:
                        State activeState = stateOperations.findByName(EState.ACTIVE)
                                .orElseThrow(() -> new RuntimeException("Error: State is not found."));
                        states.add(activeState);
                }
            });
        }

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleOperations.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleOperations.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleOperations.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        messengerUser.setRoles(roles);
        messengerUser.setStates(states);
        userOperations.save(messengerUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    //TODO
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

      //  return ResponseEntity.ok(new JwtResponse(jwt, roles));
        return ResponseEntity.ok(jwt);
    }

}



