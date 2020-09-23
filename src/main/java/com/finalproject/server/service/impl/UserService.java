package com.finalproject.server.service.impl;

import com.finalproject.server.entity.User;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserOperations, UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

//    @Override
//    public boolean isUserHaveAccess(Long id, String key) {
//    }

    @Override
    public boolean isExistByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void banUser(Long id){
        List<User> userList = (List<User>) userRepository.findAll();
        userList.forEach(user -> {
            if(user.getId().equals(id)){
                user.setBanned(true);
            }
        });
    }

    public void unBanUser(Long id){
        List<User> userList = (List<User>) userRepository.findAll();
        userList.forEach(user -> {
            if(user.getId().equals(id)){
                user.setBanned(false);
            }
        });
    }

    @Override
    public Long save(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void updateAll(Iterable<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}