package com.tavola.backend.repository;

import com.tavola.backend.model.RequestStatus;
import com.tavola.backend.model.ServiceRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByTableNumberOrderByCreatedAtDesc(Integer tableNumber);
    List<ServiceRequest> findByStatus(RequestStatus status);
}
