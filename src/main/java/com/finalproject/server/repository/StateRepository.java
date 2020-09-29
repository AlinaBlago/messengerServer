package com.finalproject.server.repository;

import com.finalproject.server.entity.EState;
import com.finalproject.server.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(EState name);
}
