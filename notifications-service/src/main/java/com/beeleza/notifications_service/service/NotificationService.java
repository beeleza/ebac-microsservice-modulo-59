package com.beeleza.notifications_service.service;

import com.beeleza.notifications_service.domain.Notification;
import com.beeleza.notifications_service.dto.NotificationRequestDTO;
import com.beeleza.notifications_service.dto.NotificationResponseDTO;
import com.beeleza.notifications_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public String send(NotificationRequestDTO request) {
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setSendAt(request.getSendAt());
        repository.save(notification);
        return "Notificação enviada com sucesso!";
    }

    public List<NotificationResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(NotificationResponseDTO::fromEntity)
                .toList();
    }
}
