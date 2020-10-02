package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private EState name;

    @ManyToMany(mappedBy = "states")
    private Set<MessengerUser> messengerUsers = new HashSet<MessengerUser>();

    public State() {
    }

    public State(Long id, EState name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EState getName() {
        return name;
    }

    public void setName(EState name) {
        this.name = name;
    }

    public Set<MessengerUser> getMessengerUsers() {
        return messengerUsers;
    }

    public void setMessengerUsers(Set<MessengerUser> messengerUsers) {
        this.messengerUsers = messengerUsers;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
