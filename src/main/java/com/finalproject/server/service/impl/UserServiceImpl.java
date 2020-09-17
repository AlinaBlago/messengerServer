package com.finalproject.server.service.impl;

import com.finalproject.server.entity.User;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    KeyServiceImpl keyService = new KeyServiceImpl();
    List<User> userList = (List<User>) userRepository.findAll();

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
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

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    @Override
    public boolean isUserHaveAccess(Long id, String key) {

        if(keyService.isExistByUserId(id)){
            if(keyService.getKeyByUserId(id).getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExistByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void banUser(Long id){

        userList.forEach(user -> {
            if(user.getId().equals(id)){
                user.setBanned(true);
            }
        });
    }

    public void unBanUser(Long id){
        userList.forEach(user -> {
            if(user.getId().equals(id)){
                user.setBanned(false);
            }
        });
    }
}