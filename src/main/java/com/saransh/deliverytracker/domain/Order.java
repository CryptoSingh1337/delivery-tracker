package com.saransh.deliverytracker.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "start_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "start_latitude"))
    })
    private Point start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "end_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "end_latitude"))
    })
    private Point end;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @JsonBackReference @OneToOne(cascade = CascadeType.ALL) private Rider rider;

    @PrePersist
    public void initializeOrderStatus() {
        this.orderStatus = OrderStatus.PENDING;
    }
}
