package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private String id;
    private String status;
    private double amount;
    private String currency;
    private String description;

}
