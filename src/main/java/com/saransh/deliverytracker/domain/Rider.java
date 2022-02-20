package com.saransh.deliverytracker.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * author: CryptoSingh1337
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rider")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded private Point location;
    @Enumerated(EnumType.STRING) private RiderStatus riderStatus;
    @JsonManagedReference @OneToOne private Order order;
}
