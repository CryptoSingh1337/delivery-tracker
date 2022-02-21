package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.*;
import com.saransh.deliverytracker.exceptions.ResourceNotFoundException;
import com.saransh.deliverytracker.repository.OrderIdAndDistance;
import com.saransh.deliverytracker.repository.OrderRepository;
import com.saransh.deliverytracker.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author: CryptoSingh1337
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final SuggestionService suggestionService;

    @Override
    public List<Rider> getAllRiders() {
        List<Rider> riders = new ArrayList<>();
        riderRepository.findAll().forEach(riders::add);
        return riders;
    }

    @Override
    public List<Rider> getAllRiderByStatus(RiderStatus status) {
        return riderRepository.findAllByRiderStatusEquals(status);
    }

    @Override
    public Rider getRiderById(Integer id) {
        return riderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
    }

    @Override
    @Transactional
    public Rider saveRider(Rider rider) {
        Rider savedRider = riderRepository.save(rider);
        return getOrderForRider(savedRider);
    }

    @Override
    @Transactional
    public Rider updateRiderLocation(Integer riderId, Point location) {
        Rider rider = getRiderById(riderId);
        if (rider != null) {
            rider.setLocation(location);
            rider = riderRepository.save(rider);
        }
        return rider;
    }

    @Override
    @Transactional
    public Rider updateOrder(Integer riderId, Integer orderId) {
        Rider rider = getRiderById(riderId);
        if (rider != null && rider.getRiderStatus() == RiderStatus.FREE && rider.getOrder() == null) {
            Order order = orderService.getOrderById(orderId);
            if (order != null && order.getOrderStatus() == OrderStatus.PENDING && order.getRider() == null) {
                rider.setRiderStatus(RiderStatus.ON_THE_WAY);
                rider.setOrder(order);
                order.setRider(rider);
                rider = riderRepository.save(rider);
                orderRepository.save(order);
            }
        }
        return rider;
    }

    @Override
    @Transactional
    public Rider updateRiderStatus(Integer riderId) {
        Rider rider = getRiderById(riderId);
        if (rider != null) {
            rider.setOrder(null);
            rider.setRiderStatus(RiderStatus.FREE);
            rider = riderRepository.save(rider);
        }
        return rider;
    }

    @Override
    @Transactional
    public Rider getOrderForRider(Rider rider) {
        OrderIdAndDistance suggestedOrder = suggestionService.getNearByOrder(rider.getId());
        if (suggestedOrder != null && suggestedOrder.getId() != null) {
            Order savedOrder = orderService.getOrderById(suggestedOrder.getId());
            rider.addOrder(savedOrder);
            rider = riderRepository.save(rider);
        }
        return rider;
    }

    @Override
    public Rider getOrderForRider(Integer riderId) {
        Rider savedRider = getRiderById(riderId);
        return getOrderForRider(savedRider);
    }
}
