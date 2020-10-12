package com.finalproject.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessengerUser> messengerUsers = new HashSet<>();

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, ERole name){
        this.id = id;
        this.name = name;
    }

    public Role(ERole name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Set<MessengerUser> getGroups() {
        return messengerUsers;
    }

    public void setGroups(Set<MessengerUser> groups) {
        this.messengerUsers = groups;
    }

    @Override
    public String getAuthority() {
        return String.valueOf(getName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
