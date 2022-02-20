package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/suggest")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping(value = "/{orderId}", produces = {"application/json"})
    public ResponseEntity<?> getNearByRider(@PathVariable Integer orderId) {
        List<?> rider = suggestionService.getNearByRider(orderId);
        if (rider == null || rider.size() == 0)
            return ResponseEntity.ok("No nearby rider found");
        return ResponseEntity.ok(rider);
    }
}
