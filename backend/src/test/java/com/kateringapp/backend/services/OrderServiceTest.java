package com.kateringapp.backend.services;

import com.kateringapp.backend.data.TestDataProvider;
import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.mappers.OrderMapper;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderMapper.class, IOrderRepository.class})
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;
    @Mock
    IOrderRepository orderRepository;
    @Mock
    IOrderMapper orderMapper;

    @Test
    void testGetOrderById_shouldReturnOrderDto() {
        Long orderId = 1L;
        Order testOrder = TestDataProvider.provideOrder();
        OrderDTO orderDTO = TestDataProvider.provideOrderDTO();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));
        when(orderMapper.mapEntityToDTO(testOrder))
                .thenReturn(orderDTO);

        OrderDTO actual = orderService.getOrder(orderId);

        assertEquals(orderDTO, actual);
    }

    @Test
    void testGetOrderById_shouldThrowException() {
        assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrder(-1L));
    }

    @Test
    void testDeleteOrder_shouldDeleteOrder() {
        Long orderId = 1L;
        Order testOrder = TestDataProvider.provideOrder();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));

        orderService.deleteOrder(orderId);

        verify(orderRepository).delete(testOrder);
    }

    @Test
    void testDeleteOrder_shouldThrowException() {
        assertThrows(OrderNotFoundException.class,
                () -> orderService.deleteOrder(-1L));
    }


}
