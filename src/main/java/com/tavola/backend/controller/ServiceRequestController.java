package com.tavola.backend.controller;

import com.tavola.backend.dto.RequestCreate;
import com.tavola.backend.dto.ServiceRequestDto;
import com.tavola.backend.model.ServiceRequest;
import com.tavola.backend.repository.ServiceRequestRepository;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tables/{tableNumber}/requests")
public class ServiceRequestController {

    private final ServiceRequestRepository requestRepository;

    public ServiceRequestController(ServiceRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping
    public List<ServiceRequestDto> getHistory(@PathVariable Integer tableNumber) {
        return requestRepository.findByTableNumberOrderByCreatedAtDesc(tableNumber).stream()
                .map(ServiceRequestDto::from)
                .toList();
    }

    @PostMapping
    public ServiceRequestDto create(@PathVariable Integer tableNumber, @Valid @RequestBody RequestCreate body) {
        ServiceRequest request = new ServiceRequest(tableNumber, body.message(), LocalDateTime.now());
        return ServiceRequestDto.from(requestRepository.save(request));
    }
}
