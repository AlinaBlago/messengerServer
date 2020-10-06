package com.finalproject.server.service;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.request.UpdateUserRequest;
import com.finalproject.server.payload.response.UserResponse;

import java.util.List;
import java.util.Optional;


public interface UserOperations {

//    List<MessengerUser> findAll();
//    Optional<MessengerUser> findById(Long id);
//    void deleteById(Long id);
//    void add(MessengerUser messengerUser);
//    boolean existByUsername(String login);
//    MessengerUser findByPasswordAndLogin(String password, String login);
//    Optional<MessengerUser> findByUsername(String username);
//    void ban(Long id);
//    void unBan(Long id);
//    void save(MessengerUser messengerUser);
//    void updateAll(Iterable<MessengerUser> users);
//    void update(MessengerUser messengerUser);
//    boolean existsByEmail(String email);


    Optional<UserResponse> findById(long id);

    Optional<UserResponse> findByUsername(String email);

    UserResponse updateById(long id, UpdateUserRequest request);

    UserResponse updateByUsername(String email, UpdateUserRequest request);

    UserResponse create(SignupRequest request);

    UserResponse createAdmin(SignupRequest request);

    void deleteById(long id);

    void deleteByEmail(String email);

}
