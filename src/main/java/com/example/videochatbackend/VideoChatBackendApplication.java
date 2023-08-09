package com.example.videochatbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class VideoChatBackendApplication {

    @Value("${spring.datasource.username}")
    private static String dbUsername;
    @Value("${spring.datasource.password}")
    private static String dbPassword;
    @Value("${spring.datasource.name}")
    private static String dbName;

    public static void main(String[] args) {
        SpringApplication.run(VideoChatBackendApplication.class, args);
    }

}
