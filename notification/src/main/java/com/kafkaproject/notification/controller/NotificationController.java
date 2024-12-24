package com.kafkaproject.notification.controller;


import com.kafkaproject.notification.model.Notification;
import com.kafkaproject.notification.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Notification>> getOrderNotifications(@PathVariable String orderId) {
        return ResponseEntity.ok(notificationService.getNotificationsForOrder(orderId));
    }
}
