package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.entities.User;
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

    public void testPusher(){
        pusher.trigger("my-channel", "my-event", "PROBA");
    }

}
