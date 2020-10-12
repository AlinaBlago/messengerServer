package com.finalproject.server.service;

import com.finalproject.server.entity.EState;
import com.finalproject.server.entity.State;
import java.util.Optional;

public interface StateOperations {
    Optional<State> findByName(EState name);
}
