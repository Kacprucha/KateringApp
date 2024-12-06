package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
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
    public Payment mapDTOToEntity(PaymentRequest paymentRequest, Order order) {
        BigDecimal amount = order.getMeals()
                .stream()
                .map(Meal::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Payment.builder()
                .amount(amount)
                .currency(paymentRequest.getCurrency())
                .description(paymentRequest.getDescription())
                .status("PENDING")
                .order(order)
                .build();
    }
}
