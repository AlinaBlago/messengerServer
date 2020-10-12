package com.finalproject.server.repository;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<MessengerUser, Long> {
    Optional<MessengerUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<MessengerUser> findMessengerUsersByUsernameIsStartingWith(String username);
    List<MessengerUser> findMessengerUsersByUsernameIsStartingWithAndAndRolesEquals(String username, Role role);
}
