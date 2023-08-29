package com.example.videochatbackend.repositories;

import com.example.videochatbackend.domain.entities.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContactRequestRepository extends JpaRepository<ContactRequest, Long> {

    Optional<ContactRequest> findByRequestId(Long requestId);

    Optional<ContactRequest> findContactRequestByRequestSender_UsernameAndRequestReceiver_Username(String sender, String receiver);

    List<ContactRequest> findAllByRequestReceiver_UserId(Long receiverId);

    List<ContactRequest> findAllByRequestReceiver_Username(String receiverUsername);

    void deleteByRequestId(Long requestId);
}
