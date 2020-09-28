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
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Long save(Role role) {
        return roleRepository.save(role).getId();
    }

    @Override
    public void updateAll(Iterable<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public Role findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
