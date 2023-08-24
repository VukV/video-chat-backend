package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.domain.dtos.rtc.RTCMessage;
import com.example.videochatbackend.services.PusherService;
import com.example.videochatbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/rtc")
public class RTCController {

    private final PusherService pusherService;

    public RTCController(PusherService pusherService) {
        this.pusherService = pusherService;
    }

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody RTCMessage message) {
        pusherService.sendRtcMessage(message);
        return ResponseEntity.ok().build();
    }
}
