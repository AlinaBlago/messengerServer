package com.finalproject.server.service.impl;

import com.finalproject.server.entity.EState;
import com.finalproject.server.entity.State;
import com.finalproject.server.entity.User;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.security.UserDetailsImpl;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserOperations {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByPasswordAndLogin(String password, String login) {
        return userRepository.findByUsernameAndPassword(login, password);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void add(User user) {
        userRepository.save(user);

    }

    @Override
    public boolean existByUsername(String login) {
        return userRepository.existsByUsername(login);
    }

    public void ban(Long id){
//        List<User> userList = (List<User>) userRepository.findAll();
//        userList.forEach(user -> {
//            if(user.getId().equals(id)){
//                user.setStates(locked);
//            }
//        });
    }

    public void unBan(Long id){
//        List<User> userList = (List<User>) userRepository.findAll();
//        userList.forEach(user -> {
//            if(user.getId().equals(id)){
//                user.setStates(active);
//            }
//        });
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateAll(Iterable<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }



}