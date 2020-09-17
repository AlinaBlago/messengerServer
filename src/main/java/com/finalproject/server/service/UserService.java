package com.finalproject.server.service;

import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Iterable<User> findAll();
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
    User add(User user);
}
