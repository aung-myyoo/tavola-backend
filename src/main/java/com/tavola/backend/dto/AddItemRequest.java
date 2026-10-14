package com.tavola.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record AddItemRequest(@NotBlank String menuItemId) {
}
