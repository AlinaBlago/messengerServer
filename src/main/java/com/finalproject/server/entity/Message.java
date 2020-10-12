package com.finalproject.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "body", nullable = false)
    String body;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sender", nullable = false)
    MessengerUser sender;

    @Column(name = "date", nullable = false)
    Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_image", nullable = false)
    Image idImage;

    @Column(name = "is_read", nullable = false)
    boolean isRead;

    @Column(name = "is_received")
    boolean isReceived;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chat", nullable = false)
    Chat chat;

    public Message() {
    }

    public Message(String body, MessengerUser sender, Date date, boolean isRead, boolean isReceived, Chat id_chat){
        this.body = body;
        this.sender = sender;
        this.date = date;
        this.isRead = isRead;
        this.isReceived = isReceived;
        this.chat = id_chat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MessengerUser getSender() {
        return sender;
    }

    public void setSender(MessengerUser id_sender) {
        this.sender = id_sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Image getIdImage() {
        return idImage;
    }

    public void setIdImage(Image id_image) {
        this.idImage = id_image;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Message{" +
                "body='" + body + '\'' +
                ", id_sender=" + sender +
                ", date=" + date +
                ", chat=" + chat +
                ", id_image=" + idImage +
                '}';
    }
}
