package com.finalproject.server.repository;

import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.spi.LocaleNameProvider;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> getMessagesByReceiverAndReadFalse(Long receiverId);
    List<Message> getMessagesByReceiver_IdAndSender_Id(Long receiverId, Long senderId);
}
