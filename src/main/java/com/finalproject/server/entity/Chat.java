package com.finalproject.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_first", nullable = false)
    MessengerUser firstUser;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_second", nullable = false)
    MessengerUser secondUser;

    public Chat() {
    }

    public Chat(Long id, MessengerUser firstUser, MessengerUser secondUser) {
        this.id = id;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessengerUser getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(MessengerUser first_user) {
        this.firstUser = first_user;
    }

    public MessengerUser getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(MessengerUser second_user) {
        this.secondUser = second_user;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                '}';
    }
}
