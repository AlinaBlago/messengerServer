package com.finalproject.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message extends AbstractEntity {

    String body;
    User sender;
    User receiver;
    Date date;
    Image idImage;
    boolean isRead;

    public Message() {
    }

    public Message(String body, User sender, User receiver, Date date, boolean isRead){
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.isRead = isRead;
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
    public User getSender() {
        return sender;
    }

    public void setSender(User id_sender) {
        this.sender = id_sender;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receiver", nullable = false)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User id_receiver) {
        this.receiver = id_receiver;
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
    public Image getIdImage() {
        return idImage;
    }

    public void setIdImage(Image id_image) {
        this.idImage = id_image;
    }

    @Column(name = "is_read", nullable = false)
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", id_sender=" + sender +
                ", id_receiver=" + receiver +
                ", date=" + date +
                ", id_image=" + idImage +
                '}';
    }
}
