package com.finalproject.server.service;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;
import com.finalproject.server.payload.request.SendChangePasswordTokenRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface TokenOperations {
    Optional<Token> findById(Long id);
    void deleteById(Long id);
    void updateAll(Iterable<Token> tokens);
    public String add(SendChangePasswordTokenRequest request);
    Token findByValue(String value);
    Optional<Token> findByValueAndUser(String token, MessengerUser messengerUser);
}
