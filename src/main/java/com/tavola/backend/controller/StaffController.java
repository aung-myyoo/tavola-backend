package com.tavola.backend.controller;

import com.tavola.backend.dto.CreateStaffRequest;
import com.tavola.backend.dto.StaffDto;
import com.tavola.backend.dto.UpdateStaffRequest;
import com.tavola.backend.service.StaffService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
    public List<StaffDto> getAll() {
        return staffService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
    public StaffDto create(@Valid @RequestBody CreateStaffRequest body) {
        return staffService.create(body);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
    public StaffDto update(@PathVariable Long id, @Valid @RequestBody UpdateStaffRequest body) {
        return staffService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
    public void delete(@PathVariable Long id) {
        staffService.delete(id);
    }

    /** Staff can upload/replace their own photo; managers and owners can set anyone's. */
    @PostMapping("/{id}/photo")
    public StaffDto updatePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file, Authentication auth) {
        requireSelfOrManagement(id, auth);
        return staffService.updatePhoto(id, file);
    }

    @DeleteMapping("/{id}/photo")
    public StaffDto removePhoto(@PathVariable Long id, Authentication auth) {
        requireSelfOrManagement(id, auth);
        return staffService.removePhoto(id);
    }

    private void requireSelfOrManagement(Long targetId, Authentication auth) {
        boolean isSelf = auth.getName().equals(targetId.toString());
        boolean isManagement = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER") || a.getAuthority().equals("ROLE_OWNER"));
        if (!isSelf && !isManagement) {
            throw new AccessDeniedException("You can only edit your own photo");
        }
    }
}
