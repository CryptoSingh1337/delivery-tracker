package com.saransh.deliverytracker.repository;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAllByOrderStatusEquals(OrderStatus orderStatus);
}
