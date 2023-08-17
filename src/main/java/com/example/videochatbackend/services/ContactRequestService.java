package com.example.videochatbackend.services;

import com.example.videochatbackend.repositories.ContactRequestRepository;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactRequestService {

    private final ContactRequestRepository contactRequestRepository;
    private final UserRepository userRepository;

    public ContactRequestService(ContactRequestRepository contactRequestRepository, UserRepository userRepository) {
        this.contactRequestRepository = contactRequestRepository;
        this.userRepository = userRepository;
    }


}
