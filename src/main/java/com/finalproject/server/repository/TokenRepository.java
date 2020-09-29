package com.finalproject.server.repository;

import com.finalproject.server.entity.EToken;
import com.finalproject.server.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByName(EToken name);
    Optional<Token> findByNameAndValue(EToken name, String value);
}
