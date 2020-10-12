package com.finalproject.server.payload.response;

import com.finalproject.server.entity.Message;

import java.util.Date;

public class MessageResponse {
    private Long chatId;
    private String senderLogin;
    private Date date;
    private String message;

    public MessageResponse() {
    }
    public MessageResponse(Message msg){
//        this.chatId = msg.getChat().getId();
        this.date = msg.getDate();
        this.message = msg.getBody();
        this.senderLogin = msg.getSender().getUsername();
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public Date getDate() {
        return date;
    }
}
