package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.SendMessageRequest;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.MessageResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

public interface MessageOperations {

    Message save(MessengerUser sender, SendMessageRequest request);
    List<Message> loadChat(Optional<MessengerUser> user, UserRequest request);
    public List<MessageResponse> getNewMessages(Chat chat, MessengerUser receiver);
    public SseEmitter loadMessages(MessengerUser user);
}
