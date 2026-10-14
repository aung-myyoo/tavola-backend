package com.tavola.backend.dto;

import com.tavola.backend.model.RestaurantTable;
import java.time.LocalDateTime;

public record TableDto(
        Integer number,
        String status,
        Integer guestCount,
        LocalDateTime seatedAt,
        String serverName
) {
    public static TableDto from(RestaurantTable t) {
        return new TableDto(t.getNumber(), t.getStatus().name(), t.getGuestCount(), t.getSeatedAt(), t.getServerName());
    }
}
