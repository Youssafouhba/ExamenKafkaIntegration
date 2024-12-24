package org.commandems.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEvent {
    private String orderId;
    private String userId;
    private OrderStatus status;
    private String message;

    public enum OrderStatus {
        CREATED,
        VALIDATED,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}
