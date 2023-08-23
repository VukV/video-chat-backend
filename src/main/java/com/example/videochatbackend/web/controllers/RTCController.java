package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.services.PusherService;
import com.example.videochatbackend.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/rtc")
public class RTCController {

    private final PusherService pusherService;
    private final UserService userService;

    public RTCController(PusherService pusherService, UserService userService) {
        this.pusherService = pusherService;
        this.userService = userService;
    }
}
