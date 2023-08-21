package com.example.videochatbackend.bootstrap;

import com.example.videochatbackend.domain.builders.UserBuilder;
import com.example.videochatbackend.domain.entities.User;
import com.example.videochatbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class BootstrapData implements CommandLineRunner {

    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                .password(passwordEncoder.encode("user1"))
                .firstName("User")
                .lastName("First")
                .build();
        userRepository.save(user1);

        User user2 = new UserBuilder()
                .username("user2")
                .email("user2@example.com")
                .password(passwordEncoder.encode("user2"))
                .firstName("User")
                .lastName("Second")
                .build();
        userRepository.save(user2);

        User vuk = new UserBuilder()
                .username("vuk")
                .email("vuk@example.com")
                .password(passwordEncoder.encode("vuk123"))
                .firstName("Vuk")
                .lastName("Vukovic")
                .build();
        userRepository.save(vuk);

        User vaske = new UserBuilder()
                .username("vaske")
                .email("vaske@example.com")
                .password(passwordEncoder.encode("vaske123"))
                .firstName("Vasilije")
                .lastName("Risticevic")
                .build();
        userRepository.save(vaske);

        vuk.getContacts().add(vaske);
        userRepository.save(vuk);

        vaske.getContacts().add(vuk);
        userRepository.save(vaske);

        User bodzi = new UserBuilder()
                .username("bodzi")
                .email("bodzi@example.com")
                .password(passwordEncoder.encode("bodzi123"))
                .firstName("Bogdan")
                .lastName("Trajkovic")
                .build();
        userRepository.save(bodzi);

        System.out.println("Users created.");
    }
}
