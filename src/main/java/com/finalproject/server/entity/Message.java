package com.finalproject.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message extends AbstractEntity {

    String body;
    User id_sender;
    User id_receiver;
    Date date;
    Image id_image;

    public Message() {
    }

    public Message(String body, User id_sender, User id_receiver, Date date, Image id_image){
        this.body = body;
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.date = date;
        this.id_image = id_image;
    }

    @Column(name = "body", nullable = false)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender", nullable = false)
    public User getId_sender() {
        return id_sender;
    }

    public void setId_sender(User id_sender) {
        this.id_sender = id_sender;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receiver", nullable = false)
    public User getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(User id_receiver) {
        this.id_receiver = id_receiver;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_image", nullable = false)
    public Image getId_image() {
        return id_image;
    }

    public void setId_image(Image id_image) {
        this.id_image = id_image;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", id_sender=" + id_sender +
                ", id_receiver=" + id_receiver +
                ", date=" + date +
                ", id_image=" + id_image +
                '}';
    }
}
