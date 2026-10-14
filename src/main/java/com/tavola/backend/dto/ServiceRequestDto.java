package com.tavola.backend.dto;

import com.tavola.backend.model.ServiceRequest;
import java.time.LocalDateTime;

public record ServiceRequestDto(
        Long id,
        Integer tableNumber,
        String message,
        String status,
        LocalDateTime createdAt
) {
    public static ServiceRequestDto from(ServiceRequest r) {
        return new ServiceRequestDto(r.getId(), r.getTableNumber(), r.getMessage(), r.getStatus().name(), r.getCreatedAt());
    }
}
