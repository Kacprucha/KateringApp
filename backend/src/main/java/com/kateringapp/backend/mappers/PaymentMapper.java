package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentMapper implements IPaymentMapper {

    @Override
    public PaymentResponse mapEntityToDTO(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getPaymentId().toString())
                .amount(payment.getAmount().doubleValue())
                .currency(payment.getCurrency())
                .description(payment.getDescription())
                .status(payment.getStatus())
                .build();
    }

    @Override
    public Payment mapDTOToEntity(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(BigDecimal.valueOf(paymentRequest.getAmount()))
                .currency(paymentRequest.getCurrency())
                .description(paymentRequest.getDescription())
                .status("PENDING")
                .clientId(paymentRequest.getClientId())
                .build();
    }
}
