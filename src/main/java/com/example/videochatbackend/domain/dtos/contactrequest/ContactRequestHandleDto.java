package com.example.videochatbackend.domain.dtos.contactrequest;

public class ContactRequestHandleDto {

    private Long requestId;
    private boolean accepted;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
