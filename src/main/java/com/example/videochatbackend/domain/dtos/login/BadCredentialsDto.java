package com.example.videochatbackend.domain.dtos.login;

public class BadCredentialsDto {

    private final String message = "Invalid login credentials.";

    public String getMessage() {
        return message;
    }
}
