package com.finalproject.server.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ChangeEmailRequest {
    @NotBlank
    private String token;

    @Email
    @NotBlank
    private String email;

    public ChangeEmailRequest() {
    }

    public ChangeEmailRequest(@NotBlank String token, @Email @NotBlank String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeEmailRequest)) return false;
        ChangeEmailRequest that = (ChangeEmailRequest) o;
        return token.equals(that.token) &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, email);
    }
}
