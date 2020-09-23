package com.finalproject.server.repository;

import com.finalproject.server.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> getMessagesByReceiverAndReadFalse(Long receiverId);
    List<Message> getMessagesByReceiver_IdAndSender_Id(Long receiverId, Long senderId);
}
