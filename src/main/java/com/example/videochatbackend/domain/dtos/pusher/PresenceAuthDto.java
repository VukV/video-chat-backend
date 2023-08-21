package com.example.videochatbackend.domain.dtos.pusher;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresenceAuthDto {

    @JsonProperty("socket_id")
    private String socketId;
    @JsonProperty("channel_name")
    private String channelName;
    private Integer userId;

    public PresenceAuthDto() {
    }

    public PresenceAuthDto(String socketId, String channelName, Integer userId) {
        this.socketId = socketId;
        this.channelName = channelName;
        this.userId = userId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
