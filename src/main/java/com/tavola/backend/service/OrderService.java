package com.tavola.backend.service;

import com.tavola.backend.dto.OrderDto;
import com.tavola.backend.dto.OrderItemDto;
import com.tavola.backend.model.MenuItem;
import com.tavola.backend.model.Order;
import com.tavola.backend.model.OrderItem;
import com.tavola.backend.repository.MenuItemRepository;
import com.tavola.backend.repository.OrderRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final BigDecimal SERVICE_CHARGE_RATE = new BigDecimal("0.125");

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final AtomicInteger orderSequence = new AtomicInteger(42);

    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public OrderDto getOpenOrder(Integer tableNumber) {
        return orderRepository.findFirstByTableNumberAndPaidFalse(tableNumber)
                .map(this::toDto)
                .orElse(null);
    }

    @Transactional
    public OrderDto addItem(Integer tableNumber, String menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NoSuchElementException("Unknown menu item: " + menuItemId));

        Order order = orderRepository.findFirstByTableNumberAndPaidFalse(tableNumber)
                .orElseGet(() -> {
                    Order fresh = new Order("T" + tableNumber + "-" + orderSequence.incrementAndGet(), tableNumber, LocalDateTime.now());
                    return orderRepository.save(fresh);
                });

        OrderItem item = new OrderItem(order, menuItem, LocalDateTime.now());
        order.getItems().add(item);
        return toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto markFinished(Integer tableNumber) {
        Order order = requireOpenOrder(tableNumber);
        order.setFinishedEating(true);
        return toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto pay(Integer tableNumber, String method) {
        Order order = requireOpenOrder(tableNumber);
        order.setPaid(true);
        order.setPayMethod(method);
        order.setPaidAt(LocalDateTime.now());
        return toDto(orderRepository.save(order));
    }

    @Transactional
    public List<OrderDto> getActiveOrders() {
        return orderRepository.findByPaidFalse().stream().map(this::toDto).toList();
    }

    private Order requireOpenOrder(Integer tableNumber) {
        return orderRepository.findFirstByTableNumberAndPaidFalse(tableNumber)
                .orElseThrow(() -> new NoSuchElementException("No open order for table " + tableNumber));
    }

    private OrderDto toDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream().map(OrderItemDto::from).toList();
        BigDecimal subtotal = order.getItems().stream()
                .map(oi -> oi.getMenuItem().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal serviceCharge = subtotal.multiply(SERVICE_CHARGE_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(serviceCharge);

        return new OrderDto(
                order.getId(), order.getOrderNumber(), order.getTableNumber(), order.getCreatedAt(),
                order.isFinishedEating(), order.isPaid(), order.getPayMethod(),
                items, subtotal.setScale(2, RoundingMode.HALF_UP), serviceCharge, total.setScale(2, RoundingMode.HALF_UP)
        );
    }
}
