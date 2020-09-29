package com.finalproject.server.service;

import com.finalproject.server.entity.EToken;
import com.finalproject.server.entity.Token;

import java.util.Optional;

public interface TokenOperations {
    Optional<Token> findById(Long id);
    void deleteById(Long id);
    Long save(Token token);
    void updateAll(Iterable<Token> tokens);
    Optional<Token> findByName(EToken token);
    Optional<Token> findByNameAndValue(EToken token, String value);
}
