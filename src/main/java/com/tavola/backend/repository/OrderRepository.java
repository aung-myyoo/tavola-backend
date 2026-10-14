package com.tavola.backend.repository;

import com.tavola.backend.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findFirstByTableNumberAndPaidFalse(Integer tableNumber);
    List<Order> findByPaidFalse();
}
