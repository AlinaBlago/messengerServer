package com.finalproject.server.service.impl;

import com.finalproject.server.entity.*;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.*;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.*;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserOperations, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenOperations tokenOperations;
    private final StateRepository stateRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, TokenOperations tokenOperations, StateRepository stateRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tokenOperations = tokenOperations;
        this.stateRepository = stateRepository;
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserResponse::fromUser);
    }

    @Override
    public UserResponse updateUsername(String username, UpdateUserLoginRequest request) {
        MessengerUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> MessengerExceptions.userNotFound(username));
        return UserResponse.fromUser(updateCurrentUserLogin(user, request));


    }

    @Override
    public UserResponse updatePassword(String username, UpdateUserPasswordRequest request) {
        MessengerUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> MessengerExceptions.userNotFound(username));
        return UserResponse.fromUser(updateCurrentUserPassword(user, request));

    }

    @Override
    public UserResponse create(SignupRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getRegularUserAuthorities(), getUnactiveUserStates()));

    }

    @Override
    public UserResponse createAdmin(SignupRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getAdminAuthorities(), getRegularUserStates()));

    }

    @Override
    public void deleteByUsername(String username) throws UsernameNotFoundException {
        MessengerUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        user.setStates(getDeletedUserStates());

        try{
            loadUserByUsername(username);
        }
        finally {
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    @Override
    public void lockByUsername(UserRequest request) throws UsernameNotFoundException {
        MessengerUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + request.getUsername() + " not found"));

        user.setStates(getLockedUserStates());

        try {
            loadUserByUsername(request.getUsername());
        }
        finally {
            user.setEnabled(false);
            userRepository.save(user);
        }


    }

    @Override
    public void unLockByUsername(UserRequest request) throws UsernameNotFoundException {
        MessengerUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + request.getUsername() + " not found"));
        user.setStates(getUnactiveUserStates());
        user.setEnabled(true);
        userRepository.save(user);

        loadUserByUsername(request.getUsername());
    }

    private MessengerUser updateCurrentUserLogin(MessengerUser user, UpdateUserLoginRequest request) {
        String username = request.getUsername();
        if (userRepository.existsByUsername(username)) throw MessengerExceptions.duplicateNickname(username);
        user.setUsername(username);

        return userRepository.save(user);
    }

    private MessengerUser updateCurrentUserPassword(MessengerUser user, UpdateUserPasswordRequest request) {
        String password = request.getPassword();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw MessengerExceptions.wrongPassword();
        }
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public void updateForgottenPassword(ChangePasswordRequest request) {
        MessengerUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> MessengerExceptions.userNotFound(""));
        Token token = tokenOperations.findByValue(request.getToken());

        if (tokenOperations.findByValueAndUser(token.getValue(), user).isPresent()) {
                String password = request.getPassword();
                if (password != null) {
                    user.setPassword(passwordEncoder.encode(password));
                    tokenOperations.deleteById(tokenOperations.findByValueAndUser(token.getValue(), user).get().getId());
                    userRepository.save(user);
                }
        }
    }

    public void updateEmail(MessengerUser user, ChangeEmailRequest request) {
        Token token = tokenOperations.findByValue(request.getToken());

        if (tokenOperations.findByValueAndUser(token.getValue(), user).isPresent()) {
            String email = request.getEmail();

                user.setEmail(email);
                tokenOperations.deleteById(tokenOperations.findByValueAndUser(token.getValue(), user).get().getId());
                userRepository.save(user);

        }
    }

    private MessengerUser save(SignupRequest request, Map<ERole, Role> authorities, Map<EState, State> states) {
        var user = new MessengerUser();
        user.getRoles().putAll(authorities);
        user.setStates(states);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);
        return user;
    }

    private Map<ERole, Role> getAdminAuthorities() {
        return roleRepository.findAllByNameIn(RoleRepository.ADMIN_AUTHORITIES)
                .collect(Collectors.toMap(
                        Role::getName,
                        Function.identity(),
                        (e1, e2) -> e2,
                        () -> new EnumMap<>(ERole.class)));
    }

    private Map<ERole, Role> getRegularUserAuthorities() {
        Role authority = roleRepository
                .findByName(ERole.ROLE_USER)
                .orElseThrow(() -> MessengerExceptions.authorityNotFound(ERole.ROLE_USER.name()));
        Map<ERole, Role> authorities = new EnumMap<>(ERole.class);
        authorities.put(ERole.ROLE_USER, authority);
        return authorities;
    }

    private Map<EState, State> getDeletedUserStates() {
        State state = stateRepository
                .findByName(EState.DELETED)
                .orElseThrow(() -> MessengerExceptions.stateNotFound(EState.DELETED.name()));
        Map<EState, State> states = new EnumMap<>(EState.class);
        states.put(EState.DELETED, state);
        return states;
    }

    private Map<EState, State> getUnactiveUserStates() {
        State state = stateRepository
                .findByName(EState.UNACTIVE)
                .orElseThrow(() -> MessengerExceptions.stateNotFound(EState.UNACTIVE.name()));
        Map<EState, State> states = new EnumMap<>(EState.class);
        states.put(EState.UNACTIVE, state);
        return states;
    }

    private Map<EState, State> getLockedUserStates() {
        State state = stateRepository
                .findByName(EState.LOCKED)
                .orElseThrow(() -> MessengerExceptions.stateNotFound(EState.LOCKED.name()));
        Map<EState, State> states = new EnumMap<>(EState.class);
        states.put(EState.LOCKED, state);
        return states;
    }

    private Map<EState, State> getRegularUserStates() {
        State state = stateRepository
                .findByName(EState.ACTIVE)
                .orElseThrow(() -> MessengerExceptions.stateNotFound(EState.ACTIVE.name()));
        Map<EState, State> states = new EnumMap<>(EState.class);
        states.put(EState.ACTIVE, state);
        return states;
    }

    private void validateUniqueFields(SignupRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw MessengerExceptions.duplicateEmail(email);
        }
        String username = request.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw MessengerExceptions.duplicateNickname(username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MessengerUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        Set<ERole> roles = EnumSet.copyOf(user.getRoles().keySet());

        if(!user.isEnabled()){
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return new User(user.getUsername(), user.getPassword(), roles);
    }

}