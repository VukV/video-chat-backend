package com.example.videochatbackend.repositories;

import com.example.videochatbackend.domain.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> getAllByUsernameFromAndUsernameToOrUsernameFromAndUsernameToOrderByTimestamp(String usernameFrom1, String usernameTo1, String usernameFrom2, String usernameTo2);
}
