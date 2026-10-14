package com.tavola.backend.controller;

import com.tavola.backend.dto.ServiceRequestDto;
import com.tavola.backend.model.RequestStatus;
import com.tavola.backend.model.ServiceRequest;
import com.tavola.backend.repository.ServiceRequestRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Cross-table view of guest call-staff requests, for the floor staff dashboards. */
@RestController
@RequestMapping("/api/requests")
public class StaffRequestController {

    private final ServiceRequestRepository requestRepository;

    public StaffRequestController(ServiceRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('SERVER', 'RECEPTIONIST', 'MANAGER', 'OWNER')")
    public List<ServiceRequestDto> getPending() {
        return requestRepository.findByStatus(RequestStatus.PENDING).stream()
                .map(ServiceRequestDto::from)
                .toList();
    }

    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('SERVER', 'RECEPTIONIST', 'MANAGER', 'OWNER')")
    public ServiceRequestDto resolve(@PathVariable Long id) {
        ServiceRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Unknown request: " + id));
        request.setStatus(RequestStatus.DONE);
        return ServiceRequestDto.from(requestRepository.save(request));
    }
}
