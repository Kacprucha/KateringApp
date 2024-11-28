package com.kateringapp.backend.services.interfaces;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface IOrderService {
    OrderDTO createOrder(OrderDTO orderDTO, Jwt jwt);

    void deleteOrder(Long id);

    OrderDTO getOrder(Long id);

    List<OrderDTO> getOrders(OrderCriteria orderCriteria, Jwt jwt);

    OrderDTO updateOrder(Long id, OrderDTO orderDTO);
}
