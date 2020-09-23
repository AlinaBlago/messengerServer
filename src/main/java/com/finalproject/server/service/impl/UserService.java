package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Role;
import com.finalproject.server.entity.User;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserOperations, UserDetailsService {

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
    public boolean save(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void updateAll(Iterable<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}