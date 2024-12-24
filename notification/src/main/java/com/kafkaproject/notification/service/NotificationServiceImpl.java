package com.kafkaproject.notification.service;


import com.kafkaproject.notification.exception.NotificationProcessingException;
import com.kafkaproject.notification.model.Notification;
import com.kafkaproject.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final NotificationRepository notificationRepository;


    public void processOrderNotification(String orderId, String userId, String message) {
        try {
            Notification notification = createNotification(orderId, userId, message);
            sendNotification(notification);
            saveNotification(notification);
            log.info("Notification processed successfully for order: {}", orderId);
        } catch (Exception e) {
            log.error("Error processing notification for order: {}", orderId, e);
            throw new NotificationProcessingException("Failed to process notification", e);
        }
    }


    public List<Notification> getNotificationsForUser(String userId) {
        return notificationRepository.findByUserId(userId);
    }


    public List<Notification> getNotificationsForOrder(String orderId) {
        return notificationRepository.findByOrderId(orderId);
    }

    private Notification createNotification(String orderId, String userId, String message) {
        Notification notification = new Notification();
        notification.setOrderId(orderId);
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(Notification.NotificationStatus.PENDING);
        return notification;
    }

    private void sendNotification(Notification notification) {
        // Ici, vous implementeriez la logique d'envoi réel des notifications
        // Par exemple: email, SMS, push notification, etc.
        log.info("Sending notification to user: {}", notification.getUserId());
        notification.setStatus(Notification.NotificationStatus.SENT);
    }

    private void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public void sendNotification(String userId, String notificationMessage, String orderId) {

    }
}