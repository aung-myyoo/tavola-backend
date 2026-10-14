package com.tavola.backend.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_TYPES = Set.of("image/png", "image/jpeg", "image/webp");
    private static final long MAX_BYTES = 5 * 1024 * 1024;

    private final Path staffPhotoDir;

    public FileStorageService(@Value("${app.upload-dir}") String uploadDir) {
        this.staffPhotoDir = Path.of(uploadDir, "staff");
        try {
            Files.createDirectories(staffPhotoDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Saves the photo and returns its public URL path (e.g. "/uploads/staff/<uuid>.jpg"). */
    public String saveStaffPhoto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Photo file is empty");
        }
        if (file.getSize() > MAX_BYTES) {
            throw new IllegalArgumentException("Photo must be 5MB or smaller");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Photo must be PNG, JPEG or WEBP");
        }

        String extension = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> ".jpg";
        };
        String filename = UUID.randomUUID() + extension;

        try {
            Files.copy(file.getInputStream(), staffPhotoDir.resolve(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return "/uploads/staff/" + filename;
    }

    public void deleteStaffPhoto(String photoUrl) {
        if (photoUrl == null || !photoUrl.startsWith("/uploads/staff/")) {
            return;
        }
        String filename = photoUrl.substring("/uploads/staff/".length());
        try {
            Files.deleteIfExists(staffPhotoDir.resolve(filename));
        } catch (IOException ignored) {
            // Best-effort cleanup; a dangling file is harmless.
        }
    }
}
