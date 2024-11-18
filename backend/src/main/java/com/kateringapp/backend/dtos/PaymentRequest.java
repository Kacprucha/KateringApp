package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {

    private double amount;
    private String currency;
    private String description;
    private Long clientId;

}
