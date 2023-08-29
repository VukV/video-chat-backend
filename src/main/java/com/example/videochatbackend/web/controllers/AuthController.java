package com.example.videochatbackend.web.controllers;

import com.example.videochatbackend.domain.dtos.login.BadCredentialsDto;
import com.example.videochatbackend.domain.dtos.login.LoginRequestDto;
import com.example.videochatbackend.domain.dtos.login.LoginResponseDto;
import com.example.videochatbackend.security.jwt.JwtUtil;
import com.example.videochatbackend.services.PusherService;
import com.example.videochatbackend.services.UserService;
import com.pusher.rest.Pusher;
import com.pusher.rest.data.PresenceUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    private final Pusher pusher;
    private final PusherService pusherService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, Pusher pusher, PusherService pusherService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.pusher = pusher;
        this.pusherService = pusherService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BadCredentialsDto());
        }

        return ResponseEntity.ok(new LoginResponseDto(jwtUtil.generateToken(userService.findUserByUsername(loginRequest.getUsername()))));
    }

    @PostMapping(path = "/pusher", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> pusherAuth(@RequestParam("socket_id") String socketId, @RequestParam("channel_name") String channelName, @RequestParam("userId") Integer userId, @RequestParam("username") String username){
        pusherService.validateConnection(username, channelName);

        if(channelName.contains("presence")) {
            PresenceUser presenceUser = new PresenceUser(userId, Map.of("username", username));
            String auth = pusher.authenticate(socketId, channelName, presenceUser);
            return ResponseEntity.ok(auth);
        }
        else {
            String auth = pusher.authenticate(socketId, channelName);
            return ResponseEntity.ok(auth);
        }
    }
}
