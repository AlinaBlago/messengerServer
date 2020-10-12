package com.finalproject.server.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.server.payload.request.LoginRequest;
import com.finalproject.server.security.SecurityConstants;
import com.finalproject.server.security.properties.JWTProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTProperties jwtProperties;

    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTProperties jwtProperties, ObjectMapper objectMapper)
    {
        setAuthenticationManager(authenticationManager);
        setUsernameParameter("login");

        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        LoginRequest credentials;

        try {
            credentials = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        var authToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword()
        );

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
    {
        long now = System.currentTimeMillis();
        var principal = (UserDetails) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + jwtProperties.getExpireIn()))
                .withArrayClaim(SecurityConstants.AUTHORITIES_CLAIM, principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret().getBytes()));

        res.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.AUTH_TOKEN_PREFIX + token);
    }
}


