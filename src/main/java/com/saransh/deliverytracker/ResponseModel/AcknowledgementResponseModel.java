package com.saransh.deliverytracker.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: CryptoSingh1337
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcknowledgementResponseModel {

    private int status;
    private String message;
}
