package com.finalproject.server.service.impl;

import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.entity.Token;
import com.finalproject.server.mail.MailService;
import com.finalproject.server.payload.request.GetTokenForUpdateEmailRequest;
import com.finalproject.server.payload.request.SendChangePasswordTokenRequest;
import com.finalproject.server.payload.response.ChangePasswordResponse;
import com.finalproject.server.payload.response.MessageResponse;
import com.finalproject.server.repository.TokenRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.security.token.CustomToken;
import com.finalproject.server.service.TokenOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TokenService implements TokenOperations {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    public TokenService(TokenRepository tokenRepository, UserRepository userRepository, MailService mailService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
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
    public void updateAll(Iterable<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }

    @Override
    public String add(SendChangePasswordTokenRequest request) {
        String token = CustomToken.getToken();
        MessengerUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User " + request.getUsername() + " not found"));

        Token userToken = new Token();
        userToken.setValue(token);
        userToken.setMessengerUser(user);
        tokenRepository.save(userToken);

        mailService.sendSimpleMessage(
                user.getEmail(),
                "Your personal token for changing password",
                token
        );
        return token;

        }

    @Override
    public String add(MessengerUser user, GetTokenForUpdateEmailRequest request) {
        String token = CustomToken.getToken();

        Token userToken = new Token();
        userToken.setValue(token);
        userToken.setMessengerUser(user);
        tokenRepository.save(userToken);

        mailService.sendSimpleMessage(
                request.getEmail(),
                "Your personal token for changing email",
                token
        );
        return token;

    }

    @Override
    public Token findByValue(String value) {
        return tokenRepository.findByValue(value);
    }

    @Override
    public Optional<Token> findByValueAndUser(String token, MessengerUser messengerUser) {
        return tokenRepository.findByValueAndMessengerUser(token, messengerUser);
    }


}
