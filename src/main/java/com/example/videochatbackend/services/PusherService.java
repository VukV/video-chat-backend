package com.example.videochatbackend.services;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PusherService {

    private final Pusher pusher;

    public PusherService(Pusher pusher) {
        this.pusher = pusher;
    }
}
