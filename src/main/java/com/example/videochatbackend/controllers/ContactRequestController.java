package com.example.videochatbackend.controllers;

import com.example.videochatbackend.domain.dtos.contactrequest.ContactRequestHandleDto;
import com.example.videochatbackend.services.ContactRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contact-requests")
public class ContactRequestController {

    private final ContactRequestService contactRequestService;

    public ContactRequestController(ContactRequestService contactRequestService) {
        this.contactRequestService = contactRequestService;
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
        contactRequestService.handleContactRequest(contactRequestHandleDto);
        return ResponseEntity.ok().build();
    }
}
