package com.example.videochatbackend.bootstrap;

import com.example.videochatbackend.domain.builders.UserBuilder;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class BootstrapData implements CommandLineRunner {

    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final UserRepository userRepository;

    public BootstrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createDatabase();
        createUsers();
    }

    private void createDatabase(){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", dbUsername, dbPassword);
            statement = connection.createStatement();
            statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = 'video_chat'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) {
                statement.executeUpdate("CREATE DATABASE video_chat");
                System.out.println("Database created.");
            } else {
                System.out.println("Database already exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    System.out.println("Closed Statement.");
                }
                if (connection != null) {
                    System.out.println("Closed Connection.");
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createUsers(){
        User user1 = new UserBuilder()
                .username("user1")
                .email("user1@example.com")
                .password("user1")
                .firstName("User")
                .lastName("First")
                .build();
        userRepository.save(user1);

        User user2 = new UserBuilder()
                .username("user2")
                .email("user2@example.com")
                .password("user2")
                .firstName("User")
                .lastName("Second")
                .build();
        userRepository.save(user2);

        User vuk = new UserBuilder()
                .username("vuk")
                .email("vuk@example.com")
                .password("vuk123")
                .firstName("Vuk")
                .lastName("Vukovic")
                .build();
        userRepository.save(vuk);
    }
}
