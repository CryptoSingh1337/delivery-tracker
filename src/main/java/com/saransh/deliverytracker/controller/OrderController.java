package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.ResponseModel.AcknowledgementResponseModel;
import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import com.saransh.deliverytracker.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        if (savedOrder.getRider() == null) {
            return ResponseEntity.status(201).body(
                    Map.of("acknowledgement", AcknowledgementResponseModel.builder()
                                    .status(201)
                                    .message("Order has been created but no rider is found nearby")
                                    .build(),
                            "order", savedOrder)
            );
        }
        return ResponseEntity.status(201).body(orderService.saveOrder(order));
    }

    @GetMapping(value = "/{orderId}/update/status", produces = {"application/json"})
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId));
    }

    @GetMapping(value = "/{orderId}/get/rider", produces = {"application/json"})
    public ResponseEntity<?> getRiderForOrder(@PathVariable Integer orderId) {
        Order savedOrder = orderService.getRiderForOrder(orderId);
        if (savedOrder.getRider() == null) {
            return ResponseEntity.ok(
                    Map.of("acknowledgement", AcknowledgementResponseModel.builder()
                                    .status(200)
                                    .message("No Rider found nearby to this order")
                                    .build(),
                            "order", savedOrder)
            );
        }
        return ResponseEntity.ok(
                Map.of("rider", savedOrder.getRider())
        );
    }
}
