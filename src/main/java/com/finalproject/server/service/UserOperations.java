package com.finalproject.server.service;

import com.finalproject.server.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserOperations {

    List<User> findAll();
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
    User add(User user);
    boolean isExistByLogin(String login);
    void banUser(Long id);
    void unBanUser(Long id);
    boolean save(User user);
    void updateAll(Iterable<User> users);

}
