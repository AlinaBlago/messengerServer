package com.finalproject.server.service.impl;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;
import com.finalproject.server.repository.TokenRepository;
import com.finalproject.server.service.TokenOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TokenService implements TokenOperations {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Optional<Token> findById(Long id) {
        return tokenRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        tokenRepository.deleteById(id);
    }

    @Override
    public Long save(Token token) {
        return tokenRepository.save(token).getId();
    }

    @Override
    public void updateAll(Iterable<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }

    @Override
    public void add(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public Token findByValue(String value) {
        return tokenRepository.findByValue(value);
    }

    @Override
    public Optional<Token> findByValueAndUser(String token, MessengerUser messengerUser) {
        return tokenRepository.findByValueAndUser(token, messengerUser);
    }


}
