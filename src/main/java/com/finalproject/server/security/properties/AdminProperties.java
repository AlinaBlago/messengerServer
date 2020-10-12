package com.finalproject.server.security.properties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AdminProperties {
    private String username;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, message = "password's length must be at least 8")
    private String password;

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
}


