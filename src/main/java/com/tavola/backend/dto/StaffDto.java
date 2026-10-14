package com.tavola.backend.dto;

import com.tavola.backend.model.Staff;

public record StaffDto(
        Long id,
        String name,
        String role,
        String photoUrl,
        boolean active
) {
    public static StaffDto from(Staff s) {
        return new StaffDto(s.getId(), s.getName(), s.getRole().name(), s.getPhotoUrl(), s.isActive());
    }
}
