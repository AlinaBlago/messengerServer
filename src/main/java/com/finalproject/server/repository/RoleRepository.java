package com.finalproject.server.repository;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
