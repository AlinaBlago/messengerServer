package com.finalproject.server.service;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;

import java.util.Optional;

public interface RoleOperations {
    Optional<Role> findByName(ERole name);
}
