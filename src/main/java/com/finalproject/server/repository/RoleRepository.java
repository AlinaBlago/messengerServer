package com.finalproject.server.repository;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
