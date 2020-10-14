package com.finalproject.server.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ChangePasswordRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String token;
    @NotBlank
    private String password;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(@NotBlank String token, @NotBlank String password, @NotBlank String username) {
        this.token = token;
        this.password = password;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangePasswordRequest)) return false;
        ChangePasswordRequest that = (ChangePasswordRequest) o;
        return username.equals(that.username) &&
                token.equals(that.token) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token, password);
    }
}
