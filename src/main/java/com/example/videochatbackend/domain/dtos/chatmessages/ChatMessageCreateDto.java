package com.example.videochatbackend.domain.dtos.chatmessages;

public class ChatMessageCreateDto {

    private String usernameTo;
    private String text;

    public ChatMessageCreateDto() {
    }

    public ChatMessageCreateDto(String usernameTo, String text) {
        this.usernameTo = usernameTo;
        this.text = text;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
