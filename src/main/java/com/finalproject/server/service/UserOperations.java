package com.finalproject.server.service;

import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.request.UpdateUserLoginRequest;
import com.finalproject.server.payload.request.UpdateUserPasswordRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public interface UserOperations {
    Optional<UserResponse> findByUsername(String email);

    UserResponse updateUsername(String email, UpdateUserLoginRequest request);

    UserResponse updatePassword(String email, UpdateUserPasswordRequest request);

    UserResponse create(SignupRequest request);

    UserDetails deleteByUsername(String username) throws UsernameNotFoundException;

    UserDetails lockByUsername(UserRequest request) throws UsernameNotFoundException;

    UserDetails unLockByUsername(UserRequest request) throws UsernameNotFoundException;

    UserResponse createAdmin(SignupRequest request);
}
