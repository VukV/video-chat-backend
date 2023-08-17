package com.example.videochatbackend.controllers;

import com.example.videochatbackend.services.ContactRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/contact-requests")
public class ContactRequestController {

    private final ContactRequestService contactRequestService;

    public ContactRequestController(ContactRequestService contactRequestService) {
        this.contactRequestService = contactRequestService;
    }

    public ResponseEntity<?> sendRequest() {
        //TODO
        return null;
    }

    public ResponseEntity<?> handleRequest() {
        //TODO
        return null;
    }
}
