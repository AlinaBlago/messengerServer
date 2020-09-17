package com.finalproject.server.service.impl;

import com.finalproject.server.entity.User;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User add(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

}
