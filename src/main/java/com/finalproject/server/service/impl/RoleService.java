package com.finalproject.server.service.impl;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;
import com.finalproject.server.repository.RoleRepository;
import com.finalproject.server.service.RoleOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements RoleOperations {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
