package com.example.videochatbackend.bootstrap;

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
        //TODO
    }
}
