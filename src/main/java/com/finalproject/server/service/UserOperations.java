package com.finalproject.server.service;

import com.finalproject.server.payload.request.*;
import com.finalproject.server.payload.response.UserResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public interface UserOperations {
    Optional<UserResponse> findByUsername(String email);

    UserResponse updateUsername(String email, UpdateUserLoginRequest request);

    UserResponse updatePassword(String email, UpdateUserPasswordRequest request);

    UserResponse create(SignupRequest request);

    void deleteByUsername(String username) throws UsernameNotFoundException;

    void lockByUsername(UserRequest request) throws UsernameNotFoundException;

    void unLockByUsername(UserRequest request) throws UsernameNotFoundException;

    UserResponse createAdmin(SignupRequest request);

    void updateForgottenPassword(ChangePasswordRequest request);
}
