package com.saransh.deliverytracker.controller;

import com.saransh.deliverytracker.ResponseModel.AcknowledgementResponseModel;
import com.saransh.deliverytracker.domain.Point;
import com.saransh.deliverytracker.domain.Rider;
import com.saransh.deliverytracker.domain.RiderStatus;
import com.saransh.deliverytracker.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> saveRider(@RequestBody Rider rider) {
        Rider savedRider = riderService.saveRider(rider);
        if (savedRider.getOrder() == null) {
            return ResponseEntity.status(201).body(
                    Map.of("acknowledgement", AcknowledgementResponseModel.builder()
                            .status(201)
                            .message("Rider has been created but no order is found nearby")
                            .build(),
                            "rider", savedRider)
            );
        }
        return ResponseEntity.status(201).body(savedRider);
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
    public ResponseEntity<?> updateOrder(
            @PathVariable Integer riderId,
            @PathVariable Integer orderId) {
        Rider rider = riderService.updateOrder(riderId, orderId);
        if (rider.getOrder() != null && rider.getOrder().getId().equals(orderId)) {
            return ResponseEntity.ok(rider);
        }
        return ResponseEntity.ok(AcknowledgementResponseModel.builder()
                .status(200)
                .message("Unable to assign the this rider to given order")
                .build());
    }

    @PutMapping(value = "/{riderId}/update/status",
            produces = {"application/json"})
    public ResponseEntity<Rider> updateRiderStatus(
            @PathVariable Integer riderId) {
        return ResponseEntity.ok(riderService.updateRiderStatus(riderId));
    }

    @GetMapping(value = "/{riderId}/get/order", produces = {"application/json"})
    public ResponseEntity<?> getOrderForRider(@PathVariable Integer riderId) {
        Rider savedRider = riderService.getOrderForRider(riderId);
        if (savedRider.getOrder() == null) {
            return ResponseEntity.ok(
                    Map.of("acknowledgement", AcknowledgementResponseModel.builder()
                                    .status(200)
                                    .message("No Order found for this rider")
                                    .build(),
                            "order", savedRider)
            );
        }
        return ResponseEntity.ok(Map.of("rider", savedRider));
    }
}
