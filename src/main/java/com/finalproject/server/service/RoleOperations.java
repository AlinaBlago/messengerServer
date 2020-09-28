package com.finalproject.server.service;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;

import java.util.Optional;

public interface RoleOperations {
    Optional<Role> findById(Long id);
    void deleteById(Long id);
    Long save(Role role);
    void updateAll(Iterable<Role> roles);
    Optional<Role> findByName(ERole name);
}
