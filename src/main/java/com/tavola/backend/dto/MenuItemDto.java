package com.tavola.backend.dto;

import com.tavola.backend.model.MenuItem;
import java.math.BigDecimal;
import java.util.List;

public record MenuItemDto(
        String id,
        String category,
        String name,
        String emoji,
        BigDecimal price,
        String description,
        List<String> tags,
        int spice,
        String allergens,
        boolean available
) {
    public static MenuItemDto from(MenuItem m) {
        return new MenuItemDto(
                m.getId(), m.getCategory(), m.getName(), m.getEmoji(), m.getPrice(),
                m.getDescription(), m.getTags(), m.getSpice(), m.getAllergens(), m.isAvailable()
        );
    }
}
