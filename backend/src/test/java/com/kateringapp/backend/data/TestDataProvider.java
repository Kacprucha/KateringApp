package com.kateringapp.backend.data;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class TestDataProvider {

    public static OrderDTO provideOrderDTO() {
        return OrderDTO.builder()
                .id(1L)
                .rate(3)
                .orderStatus(OrderStatus.PENDING)
                .startingAddress("aaa")
                .destinationAddress("bbb")
                .opinion("good")
                .mealIds(Collections.emptyList())
                .build();
    }

    public static Order provideOrder() {
        return Order.builder()
                .id(1L)
                .rate(3)
                .orderStatus(OrderStatus.PENDING)
                .startingAddress("aaa")
                .destinationAddress("bbb")
                .opinion("good")
                .clientId(UUID.randomUUID())
                .build();
    }

    public static PaymentRequest providePaymentRequest() {
        return PaymentRequest.builder()
                .currency("USD")
                .description("Test Payment")
                .orderId(1L)
                .build();
    }

    public static Payment providePayment() {
        return Payment.builder()
                .paymentId(1L)
                .amount(BigDecimal.valueOf(100.0))
                .currency("USD")
                .description("Test Payment")
                .status("PENDING")
                .order(provideOrder())
                .build();
    }

    public static PaymentResponse providePaymentResponse() {
        return PaymentResponse.builder()
                .id("1")
                .amount(100.0)
                .currency("USD")
                .description("Test Payment")
                .status("PENDING")
                .build();
    }

}
