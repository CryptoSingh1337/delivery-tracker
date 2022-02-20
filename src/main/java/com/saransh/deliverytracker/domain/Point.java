package com.saransh.deliverytracker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * author: CryptoSingh1337
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Point {

    // x-axis
    private Double longitude;
    // y-axis
    private Double latitude;
}

