package com.example.videochatbackend.web.pusher;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfig {

    @Value("${pusher.appId}")
    private String appId;

    @Value("${pusher.key}")
    private String key;

    @Value("${pusher.secret}")
    private String secret;

    @Value("${pusher.cluster}")
    private String cluster;

    @Bean
    public Pusher pusher() {
        return new Pusher(appId, key, secret);
    }
}
