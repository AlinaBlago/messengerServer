package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "value", nullable = false)
    private String value;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    User user;

    public Token() {
    }

    public Token(Long id, String value){
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User id_user) {
        this.user = id_user;
    }
}
