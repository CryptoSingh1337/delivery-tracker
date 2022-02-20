package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Point;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.domain.RiderStatus;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface RiderService {

    List<Rider> getAllRiders();
    List<Rider> getAllRiderByStatus(RiderStatus status);
    Rider getRiderById(Integer id);
    Rider saveRider(Rider rider);
    Rider updateRiderLocation(Integer riderId, Point location);
    Rider updateRiderStatus(Integer riderId);
    Rider updateOrder(Integer riderId, Integer orderId);
}
