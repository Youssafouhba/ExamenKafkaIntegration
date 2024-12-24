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
public class KafkaNotificationListener {

    private final NotificationServiceImpl notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${app.kafka.order-events-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleOrderEvent(String message) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(message, OrderEvent.class);
            log.info("Received order event: {}", orderEvent);

            String notificationMessage = createNotificationMessage(orderEvent);
            notificationService.processOrderNotification(
                    orderEvent.getOrderId(),
                    orderEvent.getUserId(),
                    notificationMessage
            );
        } catch (Exception e) {
            log.error("Error processing kafka message: {}", message, e);
        }
    }

    private String createNotificationMessage(OrderEvent orderEvent) {
        return String.format(
                "Votre commande %s a été %s. Produit: %s, Quantité: %d",
                orderEvent.getOrderId(),
                orderEvent.getStatus(),
                orderEvent.getProductId(),
                orderEvent.getQuantity()
        );
    }
}