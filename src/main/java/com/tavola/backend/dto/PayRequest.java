package com.tavola.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record PayRequest(@NotBlank String method) {
}
