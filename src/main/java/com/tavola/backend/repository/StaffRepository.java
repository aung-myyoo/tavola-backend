package com.tavola.backend.repository;

import com.tavola.backend.model.Staff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByActiveTrue();
}
