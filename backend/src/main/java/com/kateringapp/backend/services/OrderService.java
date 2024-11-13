package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.services.interfaces.IOrderService;
import com.kateringapp.backend.specifications.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderMapper orderMapper;
    private final IOrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.mapDTOToEntity(orderDTO);
        order.setOrderStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        return orderMapper.mapEntityToDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return orderMapper
                .mapEntityToDTO(orderRepository.findById(id)
                        .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public List<OrderDTO> getOrders(OrderCriteria orderCriteria) {
        Specification<Order> orderSpecification = OrderSpecification.matchesCriteria(orderCriteria);

        return orderRepository.findAll(orderSpecification)
                .stream()
                .map(orderMapper::mapEntityToDTO)
                .toList();
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Order updatedOrder = orderMapper.mapDTOToEntity(orderDTO);
        updatedOrder.setId(id);

        return orderMapper
                .mapEntityToDTO(orderRepository
                        .save(updatedOrder));
    }
}
