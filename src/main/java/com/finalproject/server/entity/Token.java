package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private EToken name;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToMany(mappedBy = "tokens")
    private Set<User> users = new HashSet<User>();

    public Token() {
    }

    public Token(Long id, EToken name, String value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EToken getName() {
        return name;
    }

    public void setName(EToken name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
