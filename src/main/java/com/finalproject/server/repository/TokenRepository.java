package com.finalproject.server.repository;

import com.finalproject.server.entity.Token;
import com.finalproject.server.entity.User;
import com.finalproject.server.security.token.CustomToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
    Optional<Token> findByValueAndUser(String token, User user);

}
