package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.exceptions.ResourceNotFoundException;
import com.saransh.deliverytracker.repository.OrderIdAndDistance;
import com.saransh.deliverytracker.repository.OrderRepository;
import com.saransh.deliverytracker.repository.RiderIdAndDistance;
import com.saransh.deliverytracker.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * author: CryptoSingh1337
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;
    private final Environment env;
    private Double radius;

    @PostConstruct
    public void initializeRadius() {
        radius = env.getProperty("radius", Double.class);
    }

    @Override
    public RiderIdAndDistance getNearByRider(Integer orderId) {
        Order order = getOrderById(orderId);
        assert order != null;
        return riderRepository.getNearByRider(orderId, radius == null ? 0.0 : radius);
    }

    @Override
    public OrderIdAndDistance getNearByOrder(Integer riderId) {
        Rider rider = getRiderById(riderId);
        assert rider != null;
        return orderRepository.getNearByOrder(riderId, radius == null ? 0.0 : radius);
    }

    private Rider getRiderById(Integer riderId) {
        return riderRepository.findById(riderId)
                .orElseThrow(() -> new ResourceNotFoundException("No rider found"));
    }

    private Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("No order found"));
    }
}
