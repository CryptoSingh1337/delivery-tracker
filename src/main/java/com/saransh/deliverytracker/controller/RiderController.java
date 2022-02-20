package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.domain.Point;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.domain.RiderStatus;
import com.saransh.deliverytracker.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: CryptoSingh1337
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<Rider>> getAllRiders() {
        return ResponseEntity.ok(riderService.getAllRiders());
    }

    @GetMapping(value = "/by-status", produces = {"application/json"})
    public ResponseEntity<List<Rider>> getAllRiderByStatus(@RequestParam RiderStatus status) {
        return ResponseEntity.ok(riderService.getAllRiderByStatus(status));
    }

    @PostMapping(consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<Rider> saveRider(@RequestBody Rider rider) {
        return ResponseEntity.status(201).body(riderService.saveRider(rider));
    }

    @PutMapping(value = "/{riderId}/update/location",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<Rider> updateRiderLocation(
            @RequestBody Point location,
            @PathVariable Integer riderId) {
        return ResponseEntity.ok(riderService.updateRiderLocation(riderId, location));
    }

    @PutMapping(value = "/{riderId}/update/order/{orderId}",
            produces = {"application/json"})
    public ResponseEntity<Rider> updateOrder(
            @PathVariable Integer riderId,
            @PathVariable Integer orderId) {
        return ResponseEntity.ok(riderService.updateOrder(riderId, orderId));
    }

    @PutMapping(value = "/{riderId}/update/status",
            produces = {"application/json"})
    public ResponseEntity<Rider> updateRiderStatus(
            @PathVariable Integer riderId) {
        return ResponseEntity.ok(riderService.updateRiderStatus(riderId));
    }
}
