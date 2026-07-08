package com.beeleza.notifications_service.repository;

import com.beeleza.notifications_service.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
