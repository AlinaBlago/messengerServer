package com.finalproject.server.repository;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.MessengerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChat(Chat chat);
}
