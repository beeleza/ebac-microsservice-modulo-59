package com.beeleza.notifications_service.dto;

import com.beeleza.notifications_service.domain.Notification;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponseDTO {

    private UUID id;
    private String message;
    private LocalDateTime sendAt;

    public NotificationResponseDTO() {
    }

    public NotificationResponseDTO(UUID id, String message, LocalDateTime sendAt) {
        this.id = id;
        this.message = message;
        this.sendAt = sendAt;
    }

    public static NotificationResponseDTO fromEntity(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getSendAt()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
