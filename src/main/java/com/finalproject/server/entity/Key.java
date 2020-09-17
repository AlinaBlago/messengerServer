package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "keys_users")
public class Key {

    private User userId;
    private String key;

    public Key() {
    }

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @NaturalId
    @Column(name = "key", nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Keys{" +
                "userId=" + userId +
                ", key='" + key + '\'' +
                '}';
    }
}
