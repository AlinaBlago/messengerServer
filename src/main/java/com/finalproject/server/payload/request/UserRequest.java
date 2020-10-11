package com.finalproject.server.payload.request;

import javax.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private String username;

    public UserRequest() {
    }

    public UserRequest(@NotBlank  String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
