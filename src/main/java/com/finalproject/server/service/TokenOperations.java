package com.finalproject.server.service;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;

import java.util.Optional;

public interface TokenOperations {
    Optional<Token> findById(Long id);
    void deleteById(Long id);
    Long save(Token token);
    void updateAll(Iterable<Token> tokens);
    void add(Token token);
    Token findByValue(String value);
    Optional<Token> findByValueAndUser(String token, MessengerUser messengerUser);
}
