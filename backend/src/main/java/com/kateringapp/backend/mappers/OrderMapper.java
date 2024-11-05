package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements IOrderMapper {
    @Override
    public OrderDTO mapEntityToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .opinion(order.getOpinion())
                .rate(order.getRate())
                .startingAddress(order.getStartingAddress())
                .destinationAddress(order.getDestinationAddress())
                .build();
    }

    @Override
    public Order mapDTOToEntity(OrderDTO orderDTO) {
        return Order.builder()
                .rate(orderDTO.rate())
                .opinion(orderDTO.opinion())
                .rate(orderDTO.rate())
                .startingAddress(orderDTO.startingAddress())
                .destinationAddress(orderDTO.destinationAddress())
                .build();
    }

}
