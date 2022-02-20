package com.saransh.deliverytracker.exceptions;

/**
 * author: CryptoSingh1337
 */
public class OrderRangeExceeded extends RuntimeException {

    public OrderRangeExceeded() {
        super();
    }

    public OrderRangeExceeded(String message) {
        super(message);
    }
}
