package com.tavola.backend.repository;

import com.tavola.backend.model.OrderItem;
import com.tavola.backend.model.OrderStage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByStageNot(OrderStage stage);
}
