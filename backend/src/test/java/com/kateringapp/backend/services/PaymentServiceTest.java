package com.kateringapp.backend.services;

import com.kateringapp.backend.data.TestDataProvider;
import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import com.kateringapp.backend.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {IPaymentMapper.class, PaymentRepository.class})
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    IPaymentMapper paymentMapper;

    @Test
    void testCreatePayment_shouldReturnPaymentResponse() {
        PaymentRequest paymentRequest = TestDataProvider.providePaymentRequest();
        Payment payment = TestDataProvider.providePayment();
        PaymentResponse paymentResponse = TestDataProvider.providePaymentResponse();

        when(paymentMapper.mapDTOToEntity(paymentRequest)).thenReturn(payment);
        when(paymentMapper.mapEntityToDTO(payment)).thenReturn(paymentResponse);

        PaymentResponse actualResponse = paymentService.createPayment(paymentRequest);

        assertEquals(paymentResponse, actualResponse);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).mapDTOToEntity(paymentRequest);
        verify(paymentMapper).mapEntityToDTO(payment);
    }

    @Test
    void testProcessPayment_shouldUpdateStatusAndReturnPaymentResponse() {
        String id = "1";
        Payment payment = TestDataProvider.providePayment();
        PaymentResponse paymentResponse = TestDataProvider.providePaymentResponse();

        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.of(payment));
        when(paymentMapper.mapEntityToDTO(payment)).thenReturn(paymentResponse);

        PaymentResponse actualResponse = paymentService.processPayment(id);

        assertEquals(paymentResponse, actualResponse);
        verify(paymentRepository).findById(Long.parseLong(id));
        verify(paymentRepository).save(payment);
        verify(paymentMapper).mapEntityToDTO(payment);

        assertTrue(payment.getStatus().equals("SUCCESS") || payment.getStatus().equals("FAILED"));
    }

    @Test
    void testProcessPayment_shouldThrowExceptionWhenPaymentNotFound() {
        String id = "99";

        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(id));
        verify(paymentRepository).findById(Long.parseLong(id));
    }

    @Test
    void testGetPaymentStatus_shouldReturnPaymentResponse() {
        String id = "1";
        Payment payment = TestDataProvider.providePayment();
        PaymentResponse paymentResponse = TestDataProvider.providePaymentResponse();

        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.of(payment));
        when(paymentMapper.mapEntityToDTO(payment)).thenReturn(paymentResponse);

        PaymentResponse actualResponse = paymentService.getPaymentStatus(id);

        assertEquals(paymentResponse, actualResponse);
        verify(paymentRepository).findById(Long.parseLong(id));
        verify(paymentMapper).mapEntityToDTO(payment);
    }

    @Test
    void testGetPaymentStatus_shouldThrowExceptionWhenPaymentNotFound() {
        String id = "99";

        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.getPaymentStatus(id));
        verify(paymentRepository).findById(Long.parseLong(id));
    }
}
