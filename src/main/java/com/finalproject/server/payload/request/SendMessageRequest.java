package com.finalproject.server.payload.request;

import javax.validation.constraints.NotBlank;

public class SendMessageRequest {
    @NotBlank
    private String receiver;
    @NotBlank
    private String message;

    public SendMessageRequest() {
    }

    public SendMessageRequest(@NotBlank String receiver, @NotBlank String message) {
        this.receiver = receiver;
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "receiver=" + receiver +
                ", message='" + message + '\'' +
                '}';
    }
}
