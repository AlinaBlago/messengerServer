package com.finalproject.server.service;

import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserOperations {

    List<User> findAll();
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
    User add(User user);
    User findUserByLogin(String login);
    User findUserByLoginAndPassword(String login, String password);
    boolean isUserHaveAccess(Long id, String key);
    boolean isExistByLogin(String login);
    void banUser(Long id);
    void unBanUser(Long id);
    Long save(User user);
    void updateAll(Iterable<User> users);

}
