package com.finalproject.server.service.impl;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.UserOperations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
    public List<MessengerUser> findAll() {
        return (List<MessengerUser>) userRepository.findAll();
    }

    @Override
    public Optional<MessengerUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public MessengerUser findByPasswordAndLogin(String password, String login) {
        return userRepository.findByUsernameAndPassword(login, password);
    }

    @Override
    public Optional<MessengerUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void add(MessengerUser user) {
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
    public void save(MessengerUser user) {
        userRepository.save(user);
    }

    @Override
    public void updateAll(Iterable<MessengerUser> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void update(MessengerUser user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MessengerUser user = userRepository.findByEmailOrNickname(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        Set<ERole> authorities = EnumSet.copyOf(user.getAuthorities().keySet());

        return new User(user.getEmail(), user.getPassword(), authorities);
    }


}