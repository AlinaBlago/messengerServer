package com.finalproject.server.controller;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;
import com.finalproject.server.entity.User;
import com.finalproject.server.payload.request.LoginRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.response.JwtResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.security.UserDetailsImpl;
import com.finalproject.server.security.jwt.JwtUtils;
import com.finalproject.server.service.RoleOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final UserOperations userOperations;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleOperations roleOperations;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthorizationController(UserOperations userOperations, BCryptPasswordEncoder bCryptPasswordEncoder, RoleOperations roleOperations, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userOperations = userOperations;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleOperations = roleOperations;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping(value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest request) {
//        if (userOperations.findByLogin(request.getLogin()) == null) {
//            User user = new User();
//            user.setEmail(request.getName());
//            user.setRoles(roleOperations.findById(1L).stream().collect(Collectors.toSet()));
//            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
//            user.setUsername(request.getLogin());
//            user.setBanned(false);
//            user.setEnabled(true);
//            userOperations.add(user);
//            userOperations.save(user);
//            userOperations.update(user);
//            return new ResponseEntity(HttpStatus.CREATED);
//        } else return new ResponseEntity(HttpStatus.OK);

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

            // Create new user's account
            User user = new User(request.getUsername(),
                    request.getEmail(),
                    bCryptPasswordEncoder.encode(request.getPassword()),
                    false);

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

            user.setRoles(roles);
            userOperations.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
//        if (bCryptPasswordEncoder.matches(request.getPassword(), userOperations.findByLogin(request.getLogin()).getPassword())) {
//            return ResponseEntity.ok("User is logined on");
//        }
//            return new ResponseEntity(HttpStatus.CONFLICT);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}

