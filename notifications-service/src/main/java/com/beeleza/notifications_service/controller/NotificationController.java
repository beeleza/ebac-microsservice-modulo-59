package com.beeleza.notifications_service.controller;

import com.beeleza.notifications_service.dto.NotificationRequestDTO;
import com.beeleza.notifications_service.dto.NotificationResponseDTO;
import com.beeleza.notifications_service.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String send(@RequestBody @Valid NotificationRequestDTO request) {
        return notificationService.send(request);
    }

    @GetMapping
    public List<NotificationResponseDTO> listAll() {
        return notificationService.listAll();
    }
}
