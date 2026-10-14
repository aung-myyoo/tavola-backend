package com.tavola.backend.dto;

public record LoginResponse(
        String token,
        StaffDto staff
) {
}
