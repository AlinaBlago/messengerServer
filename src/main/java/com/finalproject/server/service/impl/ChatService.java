package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Chat;
import com.finalproject.server.entity.MessengerUser;
import com.finalproject.server.payload.request.AddChatRequest;
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
    public Iterable<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        chatRepository.deleteById(id);
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public ChatResponse addChat(Optional<MessengerUser> user, AddChatRequest request) {
        MessengerUser msgUser = user.get();
        Optional<MessengerUser> secondUser = userRepository.findByUsername(request.getUsername());

        if (secondUser.isPresent()) {
            var leftList = chatRepository.findChatsByFirstUser(msgUser);
            leftList.retainAll(chatRepository.findChatsBySecondUser(secondUser.get()));

            var swapLeftList = chatRepository.findChatsByFirstUser(secondUser.get());
            swapLeftList.retainAll(chatRepository.findChatsBySecondUser(msgUser));

            if (leftList.size() == 0 && swapLeftList.size() == 0)
            {
                Chat chat = new Chat();
                chat.setFirstUser(msgUser);
                chat.setSecondUser(secondUser.get());
                chatRepository.save(chat);
                ChatResponse resp = new ChatResponse(chat.getId(), chat.getFirstUser().getUsername(), chat.getSecondUser().getUsername());
                return resp;
            }
        }
        return null;
    }

    @Override
    public void updateAll(Iterable<Chat> chats) {
        chatRepository.saveAll(chats);
    }

    @Override
    public List<Chat> findAllByIdFirst(MessengerUser user) {
        return chatRepository.findChatsByFirstUser(user);
    }

    @Override
    public List<Chat> findAllByIdSecond(MessengerUser user) {
        return chatRepository.findChatsBySecondUser(user);
    }

    public List<Chat> findChats(MessengerUser user){
        List<Chat> chats = Stream.of(findAllByIdFirst(user), findAllByIdSecond(user
        ))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return chats;
    }

//    @Override
//    public Optional<Chat> findByIdFirst(Long id) {
//        return chatRepository.findById_first(id);
//    }
//
//    @Override
//    public Optional<Chat> findByIdSecond(Long id) {
//        return chatRepository.findById_second(id);
//    }
}
