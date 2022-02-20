package com.saransh.deliverytracker.service;

import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.repository.RiderIdAndDistance;
import com.saransh.deliverytracker.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: CryptoSingh1337
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final RiderRepository riderRepository;
    private final Environment env;

    @Override
    public List<?> getNearByRider(Integer orderId) {
        Double radius = env.getProperty("radius", Double.class);
        List<?> list = riderRepository.getNearByRider(orderId, radius == null ? 0.0 : radius);
        return list;
    }
}
