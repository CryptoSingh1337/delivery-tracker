package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.Point;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.domain.RiderStatus;
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
                .orElseThrow(() -> new RuntimeException("Rider not found"));
    }

    @Override
    @Transactional
    public Rider saveRider(Rider rider) {
        rider.setRiderStatus(RiderStatus.FREE);
        return riderRepository.save(rider);
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
        Order order = orderService.getOrderById(orderId);
        if (rider != null && order != null) {
            rider.setRiderStatus(RiderStatus.ON_THE_WAY);
            rider.setOrder(order);
            order.setRider(rider);
            rider = riderRepository.save(rider);
            orderService.saveOrder(order);
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
}
