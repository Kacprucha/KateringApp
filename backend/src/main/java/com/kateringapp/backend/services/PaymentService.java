package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import com.kateringapp.backend.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IPaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, IPaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentMapper.mapDTOToEntity(paymentRequest);

        paymentRepository.save(payment);

        return paymentMapper.mapEntityToDTO(payment);
    }

    public PaymentResponse processPayment(String id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(Long.parseLong(id));
        if (paymentOptional.isEmpty()) {
            throw new RuntimeException("Payment not found");
        }

        Payment payment = paymentOptional.get();

        if (Math.random() > 0.2) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
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
