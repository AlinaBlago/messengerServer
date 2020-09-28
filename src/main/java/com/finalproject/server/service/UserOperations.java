package com.finalproject.server.service;

import com.finalproject.server.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface UserOperations extends UserDetailsService {

    List<User> findAll();
    Optional<User> findById(Long id);
    void deleteById(Long id);
    void add(User user);
    boolean existByUsername(String login);
    User findByPasswordAndLogin(String password, String login);
    void ban(Long id);
    void unBan(Long id);
    void save(User user);
    void updateAll(Iterable<User> users);
    void update(User user);

}
