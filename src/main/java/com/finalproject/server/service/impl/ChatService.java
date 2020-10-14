package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.UserRequest;
import com.finalproject.server.payload.response.ChatResponse;
import com.finalproject.server.repository.ChatRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.ChatOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ChatService implements ChatOperations {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChatResponse addChat(String email, UserRequest request) {
       MessengerUser user = userRepository.findByUsername(email).get();

        Optional<MessengerUser> secondUser = userRepository.findByUsername(request.getUsername());

        if (secondUser.isPresent()) {
            var leftList = chatRepository.findChatsByFirstUser(user);
            leftList.retainAll(chatRepository.findChatsBySecondUser(secondUser.get()));

            var swapLeftList = chatRepository.findChatsByFirstUser(secondUser.get());
            swapLeftList.retainAll(chatRepository.findChatsBySecondUser(user));

            if (leftList.size() == 0 && swapLeftList.size() == 0)
            {
                Chat chat = new Chat();
                chat.setFirstUser(user);
                chat.setSecondUser(secondUser.get());
                chatRepository.save(chat);

                return new ChatResponse(chat.getId(), chat.getFirstUser().getUsername(), chat.getSecondUser().getUsername());
            }
        }
        return null;
    }

    @Override
    public List<Chat> findChats(MessengerUser user){
        return Stream.of(chatRepository.findChatsByFirstUser(user), chatRepository.findChatsBySecondUser(user
        ))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Chat findChat(MessengerUser user, MessengerUser user2){
        return chatRepository.findChatByFirstUserAndSecondUser(user, user2);
    }

}
