package com.example.videochatbackend.controllers;

import com.example.videochatbackend.services.UserService;
import org.springframework.http.ResponseEntity;
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
                                                        @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(userService.findUsersContainingUsername(username, page, size));
    }
}
