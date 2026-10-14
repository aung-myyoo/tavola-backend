package com.tavola.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateStaffRequest(
        @NotBlank String name,
        @NotNull String role,
        boolean active
) {
}
