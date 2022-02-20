package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.exceptions.OrderRangeExceeded;
import com.saransh.deliverytracker.exceptions.ResourceNotFoundException;
import com.saransh.deliverytracker.repository.OrderRepository;
import com.saransh.deliverytracker.repository.RiderIdAndDistance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
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
    private final SuggestionService suggestionService;
    private final Environment env;

    public OrderServiceImpl(OrderRepository orderRepository,
                            @Lazy RiderService riderService,
                            SuggestionService suggestionService,
                            Environment env) {
        this.orderRepository = orderRepository;
        this.riderService = riderService;
        this.suggestionService = suggestionService;
        this.env = env;
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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        double longitudeDiff = order.getStart().getLongitude() - order.getEnd().getLongitude();
        double latitudeDiff = order.getStart().getLatitude() - order.getEnd().getLatitude();
        double distance = Math.sqrt(Math.pow(longitudeDiff, 2) + Math.pow(latitudeDiff, 2));
        Double orderRange = env.getProperty("order.range", Double.class);
        orderRange = orderRange == null ? 0.0 : orderRange;
        if (distance <= orderRange) {
            Order savedOrder =  orderRepository.save(order);
            return getRiderForOrder(savedOrder);
        }
        throw new OrderRangeExceeded("Order destination is too far");
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

    @Override
    @Transactional
    public Order getRiderForOrder(Order savedOrder) {
        RiderIdAndDistance suggestedRider = suggestionService.getNearByRider(savedOrder.getId());
        if (suggestedRider != null && suggestedRider.getId() != null) {
            Rider rider = riderService.getRiderById(suggestedRider.getId());
            rider.addOrder(savedOrder);
            savedOrder = orderRepository.save(savedOrder);
        }
        return savedOrder;
    }

    @Override
    public Order getRiderForOrder(Integer orderId) {
        Order savedOrder = getOrderById(orderId);
        return getRiderForOrder(savedOrder);
    }
}
