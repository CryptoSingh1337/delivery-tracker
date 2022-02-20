package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import com.saransh.deliverytracker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(value = "/by-status", produces = {"application/json"})
    public ResponseEntity<List<Order>> getAllOrdersByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrdersByStatus(status));
    }

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        return ResponseEntity.status(201).body(orderService.saveOrder(order));
    }

    @GetMapping(value = "/{orderId}/update/status", produces = {"application/json"})
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId));
    }
}
