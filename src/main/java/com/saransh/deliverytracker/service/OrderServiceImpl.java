package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.repository.OrderRepository;
import com.saransh.deliverytracker.repository.RiderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author: CryptoSingh1337
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RiderService riderService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            @Lazy RiderService riderService) {
        this.orderRepository = orderRepository;
        this.riderService = riderService;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    @Override
    public List<Order> getAllOrdersByStatus(OrderStatus orderStatus) {
        return orderRepository.findAllByOrderStatusEquals(orderStatus);
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Integer orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            riderService.updateRiderStatus(order.getRider().getId());
            order.setRider(null);
            order.setOrderStatus(OrderStatus.DELIVERED);
            order = orderRepository.save(order);
        }
        return order;
    }
}
