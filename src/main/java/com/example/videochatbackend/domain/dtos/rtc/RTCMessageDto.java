package com.example.videochatbackend.domain.dtos.rtc;

import com.example.videochatbackend.domain.entities.RTCMessageType;

public class RTCMessageDto {

    private RTCMessageType type;
    private String usernameFrom;
    private String usernameTo;
    private Object data;

    public RTCMessageDto() {
    }

    public RTCMessageDto(RTCMessageType type, String usernameFrom, String usernameTo, Object data) {
        this.type = type;
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
        this.data = data;
    }

    public RTCMessageType getType() {
        return type;
    }

    public void setType(RTCMessageType type) {
        this.type = type;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
