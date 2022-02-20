package com.saransh.deliverytracker.bootstrap;

import com.saransh.deliverytracker.domain.*;
import com.saransh.deliverytracker.repository.OrderRepository;
import com.saransh.deliverytracker.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final RiderRepository riderRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        if (riderRepository.count() == 0 && orderRepository.count() == 0) {
            List<Rider> riders = createRiders();
            List<Order> orders = createOrders();
            riderRepository.saveAll(riders);
            orderRepository.saveAll(orders);
        }
    }

    private List<Rider> createRiders() {
        return List.of(
                Rider.builder()
                        .location(Point.builder()
                                .longitude(6.0)
                                .latitude(33.5)
                                .build())
                        .riderStatus(RiderStatus.FREE)
                        .build(),
                Rider.builder()
                        .location(Point.builder()
                                .longitude(36.3)
                                .latitude(36.0)
                                .build())
                        .riderStatus(RiderStatus.FREE)
                        .build()
        );
    }

    private List<Order> createOrders() {
        return List.of(
                Order.builder()
                        .start(Point.builder()
                                .longitude(22.0)
                                .latitude(9.1)
                                .build())
                        .end(Point.builder()
                                .longitude(44.3)
                                .latitude(28.3)
                                .build())
                        .orderStatus(OrderStatus.PENDING)
                        .build(),
                Order.builder()
                        .start(Point.builder()
                                .longitude(38.0)
                                .latitude(32.4)
                                .build())
                        .end(Point.builder()
                                .longitude(43.2)
                                .latitude(13.0)
                                .build())
                        .orderStatus(OrderStatus.PENDING)
                        .build(),
                Order.builder()
                        .start(Point.builder()
                                .longitude(5.3)
                                .latitude(38.7)
                                .build())
                        .end(Point.builder()
                                .longitude(13.6)
                                .latitude(5.5)
                                .build())
                        .orderStatus(OrderStatus.PENDING)
                        .build()
        );
    }
}
