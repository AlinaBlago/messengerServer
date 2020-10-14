package com.finalproject.server.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private Set<String> role;

    private Set<String> state;

    public SignupRequest() {}

    public SignupRequest(@NotBlank String email, @NotBlank String username, @NotBlank String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public Set<String> getState() {
        return state;
    }

    public void setState(Set<String> state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignupRequest)) return false;
        SignupRequest that = (SignupRequest) o;
        return email.equals(that.email) &&
                username.equals(that.username) &&
                password.equals(that.password) &&
                Objects.equals(role, that.role) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, password, role, state);
    }
}
