package org.commandems.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commandems.model.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "order-notifications";

    public void sendOrderEvent(OrderEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event.getOrderId(), objectMapper.writeValueAsString(event));
            log.info("Order event sent to Kafka: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("Error sending order event to Kafka", e);
        }
    }
}
