package com.tavola.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotNull Long staffId,
        @Pattern(regexp = "\\d{4,6}", message = "PIN must be 4-6 digits") String pin
) {
}
