package com.beeleza.loan_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class NotificationRequestDTO {
    @NotBlank
    private String message;

    @NotNull
    private LocalDateTime sendAt;

    public NotificationRequestDTO() {
    }

    public NotificationRequestDTO(String message, LocalDateTime sendAt) {
        this.message = message;
        this.sendAt = sendAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }
}
