package com.tavola.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String orderNumber,
        Integer tableNumber,
        LocalDateTime createdAt,
        boolean finishedEating,
        boolean paid,
        String payMethod,
        List<OrderItemDto> items,
        BigDecimal subtotal,
        BigDecimal serviceCharge,
        BigDecimal total
) {
}
