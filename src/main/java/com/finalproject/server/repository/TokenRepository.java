package com.finalproject.server.repository;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
    Optional<Token> findByValueAndMessengerUser(String token, MessengerUser user);

}
