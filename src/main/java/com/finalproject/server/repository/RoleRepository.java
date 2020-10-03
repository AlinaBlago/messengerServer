package com.finalproject.server.repository;

import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Set<ERole> ADMIN_AUTHORITIES = EnumSet.of(ERole.ROLE_USER, ERole.ROLE_ADMIN);

    Stream<Role> findAllByNameIn(Set<ERole> names);


}
