package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.domain.dtos.rtc.RTCMessageDto;
import com.example.videochatbackend.services.PusherService;
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
    public ResponseEntity<?> sendMessage(@RequestBody RTCMessageDto message) {
        pusherService.sendRtcMessage(message);
        return ResponseEntity.ok().build();
    }
}
