package com.finalproject.server.repository;

import com.finalproject.server.entity.MessengerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MessengerUser, Long> {

    Optional<MessengerUser> findByUsername(String username);
    MessengerUser findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
