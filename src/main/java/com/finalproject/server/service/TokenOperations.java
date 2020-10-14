package com.finalproject.server.service;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;
import com.finalproject.server.payload.request.GetTokenForUpdateEmailRequest;
import com.finalproject.server.payload.request.UserRequest;

import java.util.Optional;

public interface TokenOperations {
    void deleteById(Long id);
    String add(UserRequest request);
    String add(String email, GetTokenForUpdateEmailRequest request);
    Token findByValue(String value);
    Optional<Token> findByValueAndUser(String token, MessengerUser messengerUser);
}
