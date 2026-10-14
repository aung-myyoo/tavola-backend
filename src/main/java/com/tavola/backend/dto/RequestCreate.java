package com.tavola.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCreate(@NotBlank String message) {
}
