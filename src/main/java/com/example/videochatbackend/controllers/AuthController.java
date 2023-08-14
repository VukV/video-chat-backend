package com.example.videochatbackend.controllers;

import com.example.videochatbackend.domain.dtos.login.LoginRequestDto;
import com.example.videochatbackend.domain.dtos.login.LoginResponseDto;
import com.example.videochatbackend.domain.exceptions.UnauthorizedException;
import com.example.videochatbackend.security.jwt.JwtUtil;
import com.example.videochatbackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception e){
            e.printStackTrace();
            throw new UnauthorizedException("Invalid login parameters.");
        }

        return ResponseEntity.ok(new LoginResponseDto(jwtUtil.generateToken(userService.findUserByUsername(loginRequest.getUsername()))));
    }
}
