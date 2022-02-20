package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.repository.OrderIdAndDistance;
import com.saransh.deliverytracker.repository.RiderIdAndDistance;

/**
 * author: CryptoSingh1337
 */
public interface SuggestionService {

    RiderIdAndDistance getNearByRider(Integer orderId);
    OrderIdAndDistance getNearByOrder(Integer riderId);
}
