package com.finalproject.server.payload.response;

public class ChangeEmailResponse {
    private String token;

    public ChangeEmailResponse() {
    }

    public ChangeEmailResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
