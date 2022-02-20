package com.saransh.deliverytracker.repository;

import com.saransh.deliverytracker.domain.Order;
import com.saransh.deliverytracker.domain.OrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAllByOrderStatusEquals(OrderStatus orderStatus);

    @Query(value = "select o.id, min(sqrt(power((o.start_longitude - r.longitude), 2) + power((o.start_latitude - r.latitude), 2))) as distance from Rider r, Orders o where o.order_status = 'PENDING' and o.rider_id is null and r.id = :riderId group by o.id having distance <= :radius limit 0,1",
            nativeQuery = true)
    OrderIdAndDistance getNearByOrder(@Param("riderId") Integer riderId, @Param("radius") Double radius);
}
