package com.example.videochatbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class VideoChatBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoChatBackendApplication.class, args);
    }

}
