package com.finalproject.server.service;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.ChatResponse;

import java.util.List;
import java.util.Optional;

public interface ChatOperations {
    ChatResponse addChat(Optional<MessengerUser> user, UserRequest request);
    List<Chat> findChats(MessengerUser user);
    Chat findChat(MessengerUser user, MessengerUser user2);
}
