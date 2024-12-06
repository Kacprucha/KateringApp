package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.exceptions.payment.CannotCreatePaymentException;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IPaymentMapper paymentMapper;
    private final IOrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest paymentRequest, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());

        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(paymentRequest.getOrderId()));

        if (!Objects.equals(userId, order.getClientId())) {
            throw new CannotCreatePaymentException();
        }

        Payment payment = paymentMapper.mapDTOToEntity(paymentRequest, order);

        paymentRepository.save(payment);

        return paymentMapper.mapEntityToDTO(payment);
    }

    public PaymentResponse processPayment(String id, Jwt jwt) {
        Optional<Payment> paymentOptional = paymentRepository.findById(Long.parseLong(id));
        if (paymentOptional.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        Payment payment = paymentOptional.get();

        Order order = payment.getOrder();

        if (!Objects.equals(order.getClientId(), UUID.fromString(jwt.getSubject()))) {
            throw new CannotCreatePaymentException();
        }

        if (Math.random() > 0.2) {
            payment.setStatus("PAID");
        }

        paymentRepository.save(payment);

        return paymentMapper.mapEntityToDTO(payment);
    }

    public PaymentResponse getPaymentStatus(String id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(Long.parseLong(id));
        if (paymentOptional.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        return paymentMapper.mapEntityToDTO(paymentOptional.get());
    }
}
