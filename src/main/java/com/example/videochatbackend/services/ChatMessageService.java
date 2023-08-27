package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.chatmessages.ChatMessageCreateDto;
import com.example.videochatbackend.domain.entities.ChatMessage;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.NotFoundException;
import com.example.videochatbackend.domain.exceptions.UnauthorizedException;
import com.example.videochatbackend.domain.mappers.ChatMessageMapper;
import com.example.videochatbackend.repositories.ChatMessageRepository;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final PusherService pusherService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserRepository userRepository, PusherService pusherService) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
        this.pusherService = pusherService;
    }

    public void handleMessage(ChatMessageCreateDto messageCreateDto) {
        String usernameFrom = SecurityContextHolder.getContext().getAuthentication().getName();

        User userFrom = userRepository.findByUsername(usernameFrom).orElseThrow(() -> new NotFoundException("User not found."));
        User userTo = userRepository.findByUsername(messageCreateDto.getUsernameTo()).orElseThrow(() -> new NotFoundException("User not found."));

        if(userFrom.isInContacts(messageCreateDto.getUsernameTo())) {
            ChatMessage message = ChatMessageMapper.INSTANCE.chatMessageCreateDtoToChatMessage(messageCreateDto);
            message.setUsernameFrom(usernameFrom);

            chatMessageRepository.save(message);
            pusherService.sendChatMessage(message, userTo.getUserId());
        }
        else {
            throw new UnauthorizedException("Can't send message to " + messageCreateDto.getUsernameTo());
        }
    }

    public List<ChatMessage> getChatMessages(String contactUsername) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return chatMessageRepository.getAllByUsernameFromAndUsernameToOrUsernameFromAndUsernameToOrderByTimestamp(username, contactUsername, contactUsername, username);
    }
}
