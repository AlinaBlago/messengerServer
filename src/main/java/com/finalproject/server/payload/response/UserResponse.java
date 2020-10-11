package com.finalproject.server.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.server.entity.ERole;
import com.finalproject.server.entity.MessengerUser;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.Set;

public class UserResponse {
    private long id;

    private String email;

    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    private Set<ERole> roles;

    public UserResponse() {
    }

    public static UserResponse fromUser(MessengerUser user) {
      //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z");
        var response = new UserResponse();
        response.id = user.getId();
        response.email = user.getEmail();
        response.username = user.getUsername();
        response.createdAt = user.getCreatedAt();
        response.roles = EnumSet.copyOf(user.getRoles().keySet());
        return response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

}
