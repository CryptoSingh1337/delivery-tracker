package com.saransh.deliverytracker.repository;

import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.domain.RiderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface RiderRepository extends CrudRepository<Rider, Integer> {

    List<Rider> findAllByRiderStatusEquals(RiderStatus riderStatus);
    @Query(value = "select r.id, min(sqrt(power((o.start_longitude - r.longitude), 2) + power((o.start_latitude - r.latitude), 2))) as distance from Rider r, Orders o where r.rider_status = 'FREE' and o.id = :orderId group by r.id having distance <= :radius",
            nativeQuery = true)
    List<RiderIdAndDistance> getNearByRider(@Param("orderId") Integer orderId, @Param("radius") Double radius);
}
