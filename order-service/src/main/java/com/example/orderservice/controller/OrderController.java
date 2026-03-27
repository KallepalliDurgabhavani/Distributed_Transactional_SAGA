package com.example.orderservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.entity.Order;
import com.example.orderservice.state.OrderState;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // FIX: Accept a narrowly-scoped DTO (no id/status) to prevent mass assignment.
    // @Valid enforces NotNull, @Min, @DecimalMin constraints before processing.
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setAmount(request.getAmount());

        Order createdOrder = orderService.createOrder(order);

        if (createdOrder.getStatus() == OrderState.ORDER_FAILED) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createdOrder);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}
