package com.example.videochatbackend.services;

import com.example.videochatbackend.domain.dtos.contactrequest.ContactRequestDto;
import com.example.videochatbackend.domain.dtos.contactrequest.ContactRequestHandleDto;
import com.example.videochatbackend.domain.dtos.user.UserDto;
import com.example.videochatbackend.domain.entities.ContactRequest;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.domain.exceptions.ForbiddenException;
import com.example.videochatbackend.domain.exceptions.NotFoundException;
import com.example.videochatbackend.domain.mappers.ContactRequestMapper;
import com.example.videochatbackend.domain.mappers.UserMapper;
import com.example.videochatbackend.repositories.ContactRequestRepository;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactRequestService {

    private final ContactRequestRepository contactRequestRepository;
    private final UserRepository userRepository;

    public ContactRequestService(ContactRequestRepository contactRequestRepository, UserRepository userRepository) {
        this.contactRequestRepository = contactRequestRepository;
        this.userRepository = userRepository;
    }

    public List<ContactRequestDto> findContactRequestsForUser(String username){
        List<ContactRequest> contactRequests = contactRequestRepository.findAllByRequestReceiver_Username(username);
        return contactRequests.stream().map(ContactRequestMapper.INSTANCE::contactRequestToContactRequestDto).collect(Collectors.toList());
    }

    @Transactional
    public void createContactRequest(String receiverUsername) {
        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ContactRequest> existingRequest = contactRequestRepository.findContactRequestByRequestSender_UsernameAndRequestReceiver_Username(senderUsername, receiverUsername);

        if(existingRequest.isEmpty()){
            User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new NotFoundException("User not found."));
            User receiver = userRepository.findByUsername(receiverUsername).orElseThrow(() -> new NotFoundException("User not found."));

            ContactRequest newRequest = new ContactRequest();
            newRequest.setRequestSender(sender);
            newRequest.setRequestReceiver(receiver);

            contactRequestRepository.save(newRequest);
        }
    }

    @Transactional
    public HashMap<String, UserDto> handleContactRequest(ContactRequestHandleDto contactRequestHandleDto) {
        ContactRequest contactRequest = contactRequestRepository.findByRequestId(contactRequestHandleDto.getRequestId()).orElseThrow(() -> new NotFoundException("Contact request not found."));

        if(!contactRequest.getRequestReceiver().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new ForbiddenException("You can't access provided contact request.");
        }

        User sender = contactRequest.getRequestSender();
        User receiver = contactRequest.getRequestReceiver();

        if(contactRequestHandleDto.isAccepted()){
            if(!sender.getContacts().contains(receiver)){
                sender.getContacts().add(receiver);
                userRepository.save(sender);
            }

            if(!receiver.getContacts().contains(sender)){
                receiver.getContacts().add(sender);
                userRepository.save(receiver);
            }
        }

        if(contactRequestRepository.existsById(contactRequestHandleDto.getRequestId())){
            contactRequestRepository.deleteByRequestId(contactRequestHandleDto.getRequestId());
        }

        HashMap<String, UserDto> senderReceiverMap = new HashMap<>(2);
        senderReceiverMap.put("sender", UserMapper.INSTANCE.userToUserDto(sender));
        senderReceiverMap.put("receiver", UserMapper.INSTANCE.userToUserDto(receiver));

        return senderReceiverMap;
    }
}
