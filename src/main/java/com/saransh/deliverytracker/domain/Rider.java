package com.saransh.deliverytracker.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
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
    @JsonManagedReference @OneToOne(cascade = CascadeType.ALL) private Order order;

    @PostConstruct
    public void initializeRiderStatus() {
        this.riderStatus = RiderStatus.FREE;
    }

    public void addOrder(Order order) {
        this.riderStatus = RiderStatus.ON_THE_WAY;
        this.order = order;
        order.setRider(this);
    }
}
