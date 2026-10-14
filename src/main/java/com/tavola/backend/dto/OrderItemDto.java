package com.tavola.backend.dto;

import com.tavola.backend.model.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderItemDto(
        Long lineId,
        String menuItemId,
        String name,
        BigDecimal price,
        String stage,
        LocalDateTime addedAt
) {
    public static OrderItemDto from(OrderItem oi) {
        return new OrderItemDto(
                oi.getId(),
                oi.getMenuItem().getId(),
                oi.getMenuItem().getName(),
                oi.getMenuItem().getPrice(),
                oi.getStage().name(),
                oi.getAddedAt()
        );
    }
}
