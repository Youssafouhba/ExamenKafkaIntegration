package com.kafkaproject.notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkaproject.notification.model.OrderEvent;
import com.kafkaproject.notification.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderNotificationListener {

    private final NotificationServiceImpl notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "order-notifications",
            groupId = "notification-service"
    )
    public void handleOrderNotification(String orderEventJson) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(orderEventJson, OrderEvent.class);
            log.info("Received order notification: {}", orderEvent);

            // Créer et envoyer la notification
            String notificationMessage = generateNotificationMessage(orderEvent);
            notificationService.sendNotification(
                    orderEvent.getUserId(),
                    notificationMessage,
                    orderEvent.getOrderId()
            );

        } catch (Exception e) {
            log.error("Error processing order notification", e);
        }
    }

    private String generateNotificationMessage(OrderEvent event) {
        return switch (event.getStatus()) {
            case CREATED -> String.format("Commande %s créée avec succès", event.getOrderId());
            case VALIDATED -> String.format("Commande %s validée", event.getOrderId());
            case PROCESSING -> String.format("Commande %s en cours de traitement", event.getOrderId());
            case COMPLETED -> String.format("Commande %s complétée", event.getOrderId());
            case FAILED -> String.format("Échec de la commande %s: %s", event.getOrderId(), event.getMessage());
            default -> String.format("Mise à jour de la commande %s: %s", event.getOrderId(), event.getStatus());
        };
    }
}