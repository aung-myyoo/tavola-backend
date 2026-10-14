package com.tavola.backend.scheduler;

import com.tavola.backend.model.OrderItem;
import com.tavola.backend.model.OrderStage;
import com.tavola.backend.model.RequestStatus;
import com.tavola.backend.model.ServiceRequest;
import com.tavola.backend.repository.OrderItemRepository;
import com.tavola.backend.repository.ServiceRequestRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mirrors the demo timers the React frontend used to run client-side:
 * order items advance a stage every 7s, staff call requests resolve after 6s.
 */
@Component
public class OrderProgressScheduler {

    private static final OrderStage[] STAGES = OrderStage.values();
    private static final long SECONDS_PER_STAGE = 7;
    private static final long REQUEST_RESOLVE_SECONDS = 6;

    private final OrderItemRepository orderItemRepository;
    private final ServiceRequestRepository requestRepository;

    public OrderProgressScheduler(OrderItemRepository orderItemRepository, ServiceRequestRepository requestRepository) {
        this.orderItemRepository = orderItemRepository;
        this.requestRepository = requestRepository;
    }

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void advanceOrderStages() {
        for (OrderItem item : orderItemRepository.findByStageNot(OrderStage.SERVED)) {
            long elapsedSeconds = Duration.between(item.getAddedAt(), LocalDateTime.now()).toSeconds();
            int targetIndex = Math.min(STAGES.length - 1, (int) (elapsedSeconds / SECONDS_PER_STAGE));
            if (targetIndex != item.getStage().ordinal()) {
                item.setStage(STAGES[targetIndex]);
                orderItemRepository.save(item);
            }
        }
    }

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void resolvePendingRequests() {
        for (ServiceRequest request : requestRepository.findByStatus(RequestStatus.PENDING)) {
            long elapsedSeconds = Duration.between(request.getCreatedAt(), LocalDateTime.now()).toSeconds();
            if (elapsedSeconds >= REQUEST_RESOLVE_SECONDS) {
                request.setStatus(RequestStatus.DONE);
                requestRepository.save(request);
            }
        }
    }
}
