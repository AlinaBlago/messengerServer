package com.finalproject.server.service.impl;

import com.finalproject.server.entity.EState;
import com.finalproject.server.entity.State;
import com.finalproject.server.repository.StateRepository;
import com.finalproject.server.service.StateOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StateService implements StateOperations {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public Optional<State> findByName(EState name) {
        return stateRepository.findByName(name);
    }
}
