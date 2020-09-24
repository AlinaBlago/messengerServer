package com.finalproject.server.repository;

import com.finalproject.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByUsername(String login);
    User findByUsernameAndPassword(String username, String password);
}
