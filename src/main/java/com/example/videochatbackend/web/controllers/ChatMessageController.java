package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.domain.dtos.chatmessages.ChatMessageCreateDto;
import com.example.videochatbackend.services.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getMessagesWithUser(@PathVariable String username) {
        return ResponseEntity.ok(chatMessageService.getChatMessages(username));
    }

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessageCreateDto messageCreateDto) {
        chatMessageService.handleMessage(messageCreateDto);
        return ResponseEntity.ok().build();
    }
}
