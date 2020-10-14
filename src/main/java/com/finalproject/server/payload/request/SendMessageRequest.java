package com.finalproject.server.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendMessageRequest)) return false;
        SendMessageRequest request = (SendMessageRequest) o;
        return receiver.equals(request.receiver) &&
                message.equals(request.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, message);
    }
}
