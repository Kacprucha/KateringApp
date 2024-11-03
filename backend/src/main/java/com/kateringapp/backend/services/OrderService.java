package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.services.interfaces.IOrderService;

import java.util.List;

public class OrderService implements IOrderService {
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public OrderDTO getOrder(Long id) {
        return null;
    }

    @Override
    public List<OrderDTO> getOrders(OrderCriteria orderCriteria) {
        return null;
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }
}
