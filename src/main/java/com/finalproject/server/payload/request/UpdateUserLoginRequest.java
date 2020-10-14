package com.finalproject.server.payload.request;

import java.util.Objects;

public class UpdateUserLoginRequest {

    private String username;

    public UpdateUserLoginRequest() {
    }

    public UpdateUserLoginRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateUserLoginRequest)) return false;
        UpdateUserLoginRequest that = (UpdateUserLoginRequest) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
