package com.finalproject.server.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UpdateUserRequest {
    @Email(message = "email must be a valid email string")
    private String email;

    @Size(min = 8, message = "password's length must be at least 8")
    private String password;

    @NotBlank(message = "nickname must not be blank")
    private String username;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserRequest that = (UpdateUserRequest) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, password);
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
