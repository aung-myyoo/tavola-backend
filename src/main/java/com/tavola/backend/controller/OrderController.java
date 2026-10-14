package com.tavola.backend.controller;

import com.tavola.backend.dto.AddItemRequest;
import com.tavola.backend.dto.OrderDto;
import com.tavola.backend.dto.PayRequest;
import com.tavola.backend.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/tables/{tableNumber}/order")
    public OrderDto getOpenOrder(@PathVariable Integer tableNumber) {
        return orderService.getOpenOrder(tableNumber);
    }

    @PostMapping("/api/tables/{tableNumber}/order/items")
    public OrderDto addItem(@PathVariable Integer tableNumber, @Valid @RequestBody AddItemRequest body) {
        return orderService.addItem(tableNumber, body.menuItemId());
    }

    @PostMapping("/api/tables/{tableNumber}/order/finish")
    public OrderDto markFinished(@PathVariable Integer tableNumber) {
        return orderService.markFinished(tableNumber);
    }

    @PostMapping("/api/tables/{tableNumber}/order/pay")
    public OrderDto pay(@PathVariable Integer tableNumber, @Valid @RequestBody PayRequest body) {
        return orderService.pay(tableNumber, body.method());
    }

    @GetMapping("/api/orders/active")
    @PreAuthorize("hasAnyRole('SERVER', 'CHEF', 'MANAGER', 'OWNER')")
    public List<OrderDto> getActiveOrders() {
        return orderService.getActiveOrders();
    }
}
