package com.finalproject.server.service.impl;

import com.finalproject.server.entity.EToken;
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
    public Optional<Token> findByName(EToken token) {
        return tokenRepository.findByName(token);
    }

    @Override
    public Optional<Token> findByNameAndValue(EToken token, String value) {
        return tokenRepository.findByNameAndValue(token, value);
    }
}
