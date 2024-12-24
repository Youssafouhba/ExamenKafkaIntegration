package com.kafkaproject.notification.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String userId;
    private String message;
    private LocalDateTime createdAt;
    private String orderId;
    private NotificationStatus status;

    public enum NotificationStatus {
        SENT, PENDING, FAILED
    }
}