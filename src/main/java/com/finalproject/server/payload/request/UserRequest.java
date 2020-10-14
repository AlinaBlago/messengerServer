package com.finalproject.server.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequest)) return false;
        UserRequest that = (UserRequest) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
