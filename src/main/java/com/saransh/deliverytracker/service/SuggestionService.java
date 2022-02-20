package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Rider;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
public interface SuggestionService {

    List<?> getNearByRider(Integer orderId);
}
