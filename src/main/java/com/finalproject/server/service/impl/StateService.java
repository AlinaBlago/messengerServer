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
    public Optional<State> findById(Long id) {
        return stateRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        stateRepository.deleteById(id);
    }

    @Override
    public Long save(State state) {
        return stateRepository.save(state).getId();
    }

    @Override
    public void updateAll(Iterable<State> states) {
        stateRepository.saveAll(states);
    }

    @Override
    public Optional<State> findByName(EState name) {
        return stateRepository.findByName(name);
    }
}
