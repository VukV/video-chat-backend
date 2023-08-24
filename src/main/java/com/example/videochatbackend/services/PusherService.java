package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.rtc.RTCMessage;
import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.BadRequestException;
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
        pusher.trigger("presence-" + channelId, "accepted_request", userDto);
    }

    public void sendRtcMessage(RTCMessage rtcMessage) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!username.equals(rtcMessage.getUsernameFrom())) {
            throw new BadRequestException("Invalid message parameters.");
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
        User userTo = userRepository.findByUsername(rtcMessage.getUsernameTo()).orElseThrow(() -> new NotFoundException("User not found."));

        if(user.isInContacts(rtcMessage.getUsernameTo())) {
            String channelName = "presence-" + userTo.getUserId();
            switch(rtcMessage.getType()){
                case OFFER -> pusher.trigger(channelName, "offer", rtcMessage);
                case ANSWER -> pusher.trigger(channelName, "answer", rtcMessage);
                case REJECT -> pusher.trigger(channelName, "reject", rtcMessage);
                case CANDIDATE -> pusher.trigger(channelName, "candidate", rtcMessage);
                case HANG_UP -> pusher.trigger(channelName, "hang_up", rtcMessage);
            }
        }
        else {
            throw new UnauthorizedException("User is not in your contacts.");
        }
    }

}
