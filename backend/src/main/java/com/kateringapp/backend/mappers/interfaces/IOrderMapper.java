package com.kateringapp.backend.mappers.interfaces;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.order.Order;

public interface IOrderMapper {

    OrderDTO mapEntityToDTO(Order order);
    Order mapDTOToEntity(OrderDTO orderDTO);

}
