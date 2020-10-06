package com.finalproject.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
public class MessengerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @NaturalId
    @Email
    @Column(name = "email", nullable = false)
    String email;

    @JsonIgnore
    @NaturalId
    @Column(name = "login", nullable = false)
    String username;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    String password;

    @JsonIgnore
    @Column(name = "enabled", nullable = false)
    boolean enabled;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKey(name = "name")
    private Map<ERole, Role> roles = new EnumMap<>(ERole.class);

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_states",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_state")
    )
    private Set<State> states = new HashSet<>();

    public MessengerUser() {
    }

    public MessengerUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<ERole, Role> getRoles() {
        return roles;
    }

    public void setRoles(Map<ERole, Role> roles) {
        this.roles = roles;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", created at='" + getCreatedAt() + '\'' +
                '}';
    }

}
