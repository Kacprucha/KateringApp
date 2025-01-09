package com.kateringapp.backend.services;

import com.kateringapp.backend.data.TestDataProvider;
import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.exceptions.order.ForbiddenOrderStatusUpdateException;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.mappers.OrderMapper;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.MealRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderMapper.class, IOrderRepository.class, MealRepository.class})
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;
    @Mock
    IOrderRepository orderRepository;
    @MockBean
    MealRepository mealRepository;
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

    @Test
    void testUpdateOrder_shouldUpdateStatus_whenOrderStatusIsPending() {

        Long orderId = 1L;

        Order testOrder = TestDataProvider.provideOrder();
        OrderDTO testOrderDTO = TestDataProvider.provideOrderDTO();

        testOrder.setOrderStatus(OrderStatus.PENDING);
        testOrderDTO.setOrderStatus(OrderStatus.COMPLETED);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));
        when(orderMapper.mapDTOToEntity(testOrderDTO))
                .thenReturn(testOrder);
        when(orderRepository.save(testOrder))
                .thenReturn(testOrder);
        when(orderMapper.mapEntityToDTO(testOrder))
                .thenReturn(testOrderDTO);


        OrderDTO resultOrderDTO = orderService.updateOrder(orderId, testOrderDTO);

        assertEquals(testOrderDTO, resultOrderDTO);
    }

    @Test
    void testUpdateOrder_shouldThrowException_whenCancelledStatusIsUpdated() {

        Long orderId = 1L;

        Order testOrder = TestDataProvider.provideOrder();
        OrderDTO testOrderDTO = TestDataProvider.provideOrderDTO();

        testOrder.setOrderStatus(OrderStatus.CANCELLED);
        testOrderDTO.setOrderStatus(OrderStatus.COMPLETED);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));
        when(orderMapper.mapDTOToEntity(testOrderDTO))
                .thenReturn(testOrder);
        when(orderRepository.save(testOrder))
                .thenReturn(testOrder);
        when(orderMapper.mapEntityToDTO(testOrder))
                .thenReturn(testOrderDTO);


        assertThrows(
                ForbiddenOrderStatusUpdateException.class,
                () -> orderService.updateOrder(orderId, testOrderDTO)
        );
    }

    @Test
    void testUpdateOrder_shouldThrowException_whenCompletedStatusIsUpdated() {

        Long orderId = 1L;

        Order testOrder = TestDataProvider.provideOrder();
        OrderDTO testOrderDTO = TestDataProvider.provideOrderDTO();

        testOrder.setOrderStatus(OrderStatus.COMPLETED);
        testOrderDTO.setOrderStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));
        when(orderMapper.mapDTOToEntity(testOrderDTO))
                .thenReturn(testOrder);
        when(orderRepository.save(testOrder))
                .thenReturn(testOrder);
        when(orderMapper.mapEntityToDTO(testOrder))
                .thenReturn(testOrderDTO);


        assertThrows(
                ForbiddenOrderStatusUpdateException.class,
                () -> orderService.updateOrder(orderId, testOrderDTO)
        );
    }

    @Test
    void testUpdateOrder_shouldUpdate_whenCancelledStatusIsNotUpdated() {

        Long orderId = 1L;

        Order testOrder = TestDataProvider.provideOrder();
        OrderDTO testOrderDTO = TestDataProvider.provideOrderDTO();

        testOrder.setOrderStatus(OrderStatus.CANCELLED);
        testOrderDTO.setOrderStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(testOrder));
        when(orderMapper.mapDTOToEntity(testOrderDTO))
                .thenReturn(testOrder);
        when(orderRepository.save(testOrder))
                .thenReturn(testOrder);
        when(orderMapper.mapEntityToDTO(testOrder))
                .thenReturn(testOrderDTO);


        OrderDTO resultOrderDTO = orderService.updateOrder(orderId, testOrderDTO);

        assertEquals(testOrderDTO, resultOrderDTO);
    }
}
