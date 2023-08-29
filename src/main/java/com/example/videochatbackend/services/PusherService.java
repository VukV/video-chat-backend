package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.rtc.RTCMessageDto;
import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.domain.entities.ChatMessage;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.BadRequestException;
import com.example.videochatbackend.domain.exceptions.ForbiddenException;
import com.example.videochatbackend.domain.exceptions.NotFoundException;
import com.example.videochatbackend.domain.exceptions.UnauthorizedException;
import com.example.videochatbackend.repositories.UserRepository;
import com.pusher.rest.Pusher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PusherService {

    private final Pusher pusher;
    private final UserRepository userRepository;

    public PusherService(Pusher pusher, UserRepository userRepository) {
        this.pusher = pusher;
        this.userRepository = userRepository;
    }

    public void validateConnection(String username, String channelName) {
        if(channelName.contains("private")) {
            String[] channelParams = channelName.split("-");
            User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));

            if(!user.getUserId().equals(Long.valueOf(channelParams[1]))) {
                throw new UnauthorizedException("You can't access this channel.");
            }
        }


        if(!username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new UnauthorizedException("You can't access this channel.");
        }

        String[] channelParams = channelName.split("-");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));

        if(user.getUserId().equals(Long.valueOf(channelParams[1]))) {
            return;
        }

        if(!user.isInContacts(Long.valueOf(channelParams[1]))) {
            throw new UnauthorizedException("You can't access this channel.");
        }
    }

    public void notifyAcceptedRequest(Long channelId, UserDto userDto) {
        pusher.trigger("private-" + channelId, "accepted_request", userDto);
    }

    public void sendRtcMessage(RTCMessageDto rtcMessage) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!username.equals(rtcMessage.getUsernameFrom())) {
            throw new BadRequestException("Invalid message parameters.");
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
        User userTo = userRepository.findByUsername(rtcMessage.getUsernameTo()).orElseThrow(() -> new NotFoundException("User not found."));

        if(user.isInContacts(rtcMessage.getUsernameTo())) {
            String channelName = "private-" + userTo.getUserId();
            switch(rtcMessage.getType()){
                case OFFER -> pusher.trigger(channelName, "offer", rtcMessage);
                case ANSWER -> pusher.trigger(channelName, "answer", rtcMessage);
                case REJECT -> pusher.trigger(channelName, "reject", rtcMessage);
                case CANDIDATE -> pusher.trigger(channelName, "candidate", rtcMessage);
                case HANG_UP -> pusher.trigger(channelName, "hang_up", rtcMessage);
            }
        }
        else {
            throw new ForbiddenException("User is not in your contacts.");
        }
    }

    public void sendChatMessage(ChatMessage message, Long channelId){
        String channelName = "private-" + channelId;
        pusher.trigger(channelName, "chat_message", message);
    }

}
