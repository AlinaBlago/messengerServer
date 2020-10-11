package com.finalproject.server.repository;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
   List<Chat> findChatsByFirstUser(MessengerUser user);
   List<Chat> findChatsBySecondUser(MessengerUser user);
   Chat findChatByFirstUserAndSecondUser(MessengerUser user1, MessengerUser user2);
}
