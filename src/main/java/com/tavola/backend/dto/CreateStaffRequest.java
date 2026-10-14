package com.tavola.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateStaffRequest(
        @NotBlank String name,
        @NotNull String role,
        @Pattern(regexp = "\\d{4,6}", message = "PIN must be 4-6 digits") String pin
) {
}
