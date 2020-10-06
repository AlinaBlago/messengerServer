package com.finalproject.server.service.impl;

import com.finalproject.server.entity.*;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.ChangeEmailRequest;
import com.finalproject.server.payload.request.ChangePasswordRequest;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.request.UpdateUserRequest;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.repository.RoleRepository;
import com.finalproject.server.repository.StateRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.TokenOperations;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
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
    public Optional<UserResponse> findById(long id) {
        return userRepository.findById(id).map(UserResponse::fromUser);
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserResponse::fromUser);
    }

    @Override
    public UserResponse updateById(long id, UpdateUserRequest request) {
        MessengerUser user = getUser(id);
        return UserResponse.fromUser(updateCurrentUserCredentials(user, request));

    }

    @Override
    public UserResponse updateByUsername(String username, UpdateUserRequest request) {
        MessengerUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> MessengerExceptions.userNotFound(username));
        return UserResponse.fromUser(updateCurrentUserCredentials(user, request));


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
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        MessengerUser user = userRepository.findByUsername(username).get();
        user.setStates(getDeletedUserStates());
    }

    public void mergeAdmins(List<SignupRequest> requests) {
        if (requests.isEmpty()) return;
        Map<ERole, Role> authorities = getAdminAuthorities();
        var now = Instant.now();
        for (SignupRequest request : requests) {
            String email = request.getEmail();
            String username = request.getUsername();
            MessengerUser user = userRepository.findByEmail(email).orElseGet(() -> {
                var newUser = new MessengerUser();
                newUser.setCreatedAt(now);
                newUser.setEmail(email);
                return newUser;
            });
            if (!username.equals(user.getUsername())) {
                if (userRepository.existsByUsername(username)) throw MessengerExceptions.duplicateNickname(username);
                user.setUsername(username);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getRoles().putAll(authorities);
            userRepository.save(user);
        }
    }

    private MessengerUser getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> MessengerExceptions.userNotFound(id));
    }

    private MessengerUser updateCurrentUserCredentials(MessengerUser user, UpdateUserRequest request) {
        String username = request.getUsername();

        if (username == null) {
            String lastUsername = user.getUsername();
            user.setUsername(lastUsername);

        } else {
            if (!username.equals(user.getUsername())) {
                if (userRepository.existsByUsername(username)) throw MessengerExceptions.duplicateNickname(username);
                user.setUsername(username);
            }
        }

        String password = request.getPassword();
        if (password == null) {
            String lastPassword = user.getPassword();
            user.setPassword(lastPassword);
        } else {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    public void updateForgottenPassword(ChangePasswordRequest request) {
        Optional<MessengerUser> user = userRepository.findByUsername(request.getUsername());
        Token token = tokenOperations.findByValue(request.getToken());

        if (tokenOperations.findByValueAndUser(token.getValue(), user.get()).isPresent()) {
                String password = request.getPassword();
                if (password != null) {
                    user.get().setPassword(passwordEncoder.encode(password));
                    tokenOperations.deleteById(tokenOperations.findByValueAndUser(token.getValue(), user.get()).get().getId());
                    userRepository.save(user.get());
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(Instant.now());
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

    private Map<EState, State> getActiveUserStates() {
        State state = stateRepository
                .findByName(EState.ACTIVE)
                .orElseThrow(() -> MessengerExceptions.stateNotFound(EState.ACTIVE.name()));
        Map<EState, State> states = new EnumMap<>(EState.class);
        states.put(EState.ACTIVE, state);
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

        return new User(user.getUsername(), user.getPassword(), roles);
    }

}