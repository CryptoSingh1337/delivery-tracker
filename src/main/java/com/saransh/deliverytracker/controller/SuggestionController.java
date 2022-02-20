package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.repository.OrderIdAndDistance;
import com.saransh.deliverytracker.repository.RiderIdAndDistance;
import com.saransh.deliverytracker.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: CryptoSingh1337
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/suggest")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping(value = "/order/{orderId}", produces = {"application/json"})
    public ResponseEntity<?> getNearByRider(@PathVariable Integer orderId) {
        RiderIdAndDistance rider = suggestionService.getNearByRider(orderId);
        if (rider == null)
            return ResponseEntity.ok("No nearby rider found");
        return ResponseEntity.ok(rider);
    }

    @GetMapping(value = "/rider/{riderId}", produces = {"application/json"})
    public ResponseEntity<?> getNearByOrder(@PathVariable Integer riderId) {
        OrderIdAndDistance order = suggestionService.getNearByOrder(riderId);
        if (order == null)
            return ResponseEntity.ok("No nearby order found");
        return ResponseEntity.ok(order);
    }
}
