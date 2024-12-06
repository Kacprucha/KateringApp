package com.kateringapp.backend.mappers.interfaces;

import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.entities.order.Order;

public interface IPaymentMapper {

    PaymentResponse mapEntityToDTO(Payment payment);

    Payment mapDTOToEntity(PaymentRequest paymentRequest, Order order);
}
