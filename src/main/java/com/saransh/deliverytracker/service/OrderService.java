package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface OrderService {

    List<Order> getAllOrders();
    List<Order> getAllOrdersByStatus(OrderStatus status);
    Order getOrderById(Integer id);
    Order saveOrder(Order order);
    Order updateOrderStatus(Integer orderId);
    Order getRiderForOrder(Order order);
    Order getRiderForOrder(Integer orderId);
}
