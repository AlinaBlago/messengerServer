package com.finalproject.server.service.impl;

import com.finalproject.server.entity.User;
import com.finalproject.server.repository.EntityRepository;
import com.finalproject.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private EntityRepository entityRepository;


    @Override
    public Iterable<User> findAll() {
        return entityRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return entityRepository.findById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public User add(User user) {
        User savedUser = entityRepository.save(user);
        return savedUser;
    }

}
