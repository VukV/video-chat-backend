package com.example.videochatbackend.repositories;

import com.example.videochatbackend.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUsername(String username);

    @Query("select u from User u left join fetch u.contacts where u.username =:username")
    Optional<User> findUserContacts(@Param("username") String username);

    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

}
