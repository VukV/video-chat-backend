package com.example.videochatbackend.domain.dtos.login;


public class LoginResponseDto {

    private String jwt;

    public LoginResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
