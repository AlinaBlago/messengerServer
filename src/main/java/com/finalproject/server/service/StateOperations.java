package com.finalproject.server.service;

import com.finalproject.server.entity.EState;
import com.finalproject.server.entity.State;
import java.util.Optional;

public interface StateOperations {
    Optional<State> findById(Long id);
    void deleteById(Long id);
    Long save(State state);
    void updateAll(Iterable<State> states);
    Optional<State> findByName(EState name);
}
