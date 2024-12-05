package com.kateringapp.backend.services;

import com.kateringapp.backend.data.TestDataProvider;
import com.kateringapp.backend.dtos.PaymentRequest;
import com.kateringapp.backend.dtos.PaymentResponse;
import com.kateringapp.backend.entities.Payment;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.mappers.interfaces.IPaymentMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {IPaymentMapper.class, PaymentRepository.class})
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    IOrderRepository orderRepository;

    @Mock
    IPaymentMapper paymentMapper;

    @Test
    void testCreatePayment_shouldReturnPaymentResponse() {
        Jwt jwt = mock(Jwt.class);
        PaymentRequest paymentRequest = TestDataProvider.providePaymentRequest();
        Payment payment = TestDataProvider.providePayment();
        PaymentResponse paymentResponse = TestDataProvider.providePaymentResponse();
        Order order = TestDataProvider.provideOrder();
        UUID clientId = order.getClientId();

        when(jwt.getSubject()).thenReturn(clientId.toString());
        when(orderRepository.findById(paymentRequest.getOrderId()))
                .thenReturn(Optional.of(order));
        when(paymentMapper.mapDTOToEntity(paymentRequest, order)).thenReturn(payment);
        when(paymentMapper.mapEntityToDTO(payment)).thenReturn(paymentResponse);

        PaymentResponse actualResponse = paymentService.createPayment(paymentRequest, jwt);

        assertEquals(paymentResponse, actualResponse);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).mapDTOToEntity(paymentRequest, order);
        verify(paymentMapper).mapEntityToDTO(payment);
    }

    @Test
    void testProcessPayment_shouldUpdateStatusAndReturnPaymentResponse() {
        String id = "1";
        Jwt jwt = mock(Jwt.class);
        Payment payment = TestDataProvider.providePayment();
        PaymentResponse paymentResponse = TestDataProvider.providePaymentResponse();
        UUID clientId = payment.getOrder().getClientId();

        when(jwt.getSubject()).thenReturn(clientId.toString());
        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.of(payment));
        when(paymentMapper.mapEntityToDTO(payment)).thenReturn(paymentResponse);

        PaymentResponse actualResponse = paymentService.processPayment(id, jwt);

        assertEquals(paymentResponse, actualResponse);
        verify(paymentRepository).findById(Long.parseLong(id));
        verify(paymentRepository).save(payment);
        verify(paymentMapper).mapEntityToDTO(payment);

        assertTrue(payment.getStatus().equals("PAID") || payment.getStatus().equals("PENDING"));
    }

    @Test
    void testProcessPayment_shouldThrowExceptionWhenPaymentNotFound() {
        Jwt jwt = mock(Jwt.class);
        String id = "99";

        when(paymentRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(id, jwt));
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
