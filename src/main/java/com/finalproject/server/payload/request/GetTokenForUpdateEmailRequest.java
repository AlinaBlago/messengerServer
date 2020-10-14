package com.finalproject.server.payload.request;

import javax.validation.constraints.Email;
import java.util.Objects;

public class GetTokenForUpdateEmailRequest {
    @Email
    private String email;

    public GetTokenForUpdateEmailRequest() {
    }

    public GetTokenForUpdateEmailRequest(@Email String email) {
        this.email = email;
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
        if (!(o instanceof GetTokenForUpdateEmailRequest)) return false;
        GetTokenForUpdateEmailRequest request = (GetTokenForUpdateEmailRequest) o;
        return email.equals(request.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
