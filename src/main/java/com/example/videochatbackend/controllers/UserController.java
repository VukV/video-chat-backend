package com.example.videochatbackend.controllers;

import com.example.videochatbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsersContainingUsername(@RequestParam String username,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(userService.findUsersContainingUsername(username, page, size));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/contacts")
    public ResponseEntity<?> getContacts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.findUserContacts(username));
    }
}
