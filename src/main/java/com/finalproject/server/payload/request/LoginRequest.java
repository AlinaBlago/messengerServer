package com.finalproject.server.payload.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class LoginRequest {
    @NotBlank
    @JsonAlias({"username", "email"})
    private String username;

    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginRequest)) return false;
        LoginRequest request = (LoginRequest) o;
        return username.equals(request.username) &&
                password.equals(request.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
