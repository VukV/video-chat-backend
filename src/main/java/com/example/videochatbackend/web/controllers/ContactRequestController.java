package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.domain.dtos.contactrequest.ContactRequestHandleDto;
import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.services.ContactRequestService;
import com.example.videochatbackend.services.PusherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/api/contact-requests")
public class ContactRequestController {

    private final ContactRequestService contactRequestService;
    private final PusherService pusherService;

    public ContactRequestController(ContactRequestService contactRequestService, PusherService pusherService) {
        this.contactRequestService = contactRequestService;
        this.pusherService = pusherService;
    }

    @GetMapping
    public ResponseEntity<?> getRequests() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(contactRequestService.findContactRequestsForUser(username));
    }

    @PostMapping("/{receiverUsername}")
    public ResponseEntity<?> sendRequest(@PathVariable String receiverUsername) {
        contactRequestService.createContactRequest(receiverUsername);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/handle")
    public ResponseEntity<?> handleRequest(@RequestBody ContactRequestHandleDto contactRequestHandleDto) {
        HashMap<String, UserDto> srMap = contactRequestService.handleContactRequest(contactRequestHandleDto);

        pusherService.notifyAcceptedRequest(srMap.get("sender").getUserId(), srMap.get("receiver"));
        pusherService.notifyAcceptedRequest(srMap.get("receiver").getUserId(), srMap.get("sender"));

        return ResponseEntity.ok().build();
    }
}
