package com.example.videochatbackend.repositories;

import com.example.videochatbackend.domain.entities.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRequestRepository extends JpaRepository<ContactRequest, Long> {

    Optional<ContactRequest> findByRequestId(Long requestId);

    List<ContactRequest> findAllByRequestReceiver_UserId(Long receiverId);

    void deleteByRequestId(Long requestId);
}
