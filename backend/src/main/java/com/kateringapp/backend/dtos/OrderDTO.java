package com.kateringapp.backend.dtos;

import com.kateringapp.backend.entities.order.OrderStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderDTO(Long id,
                       List<Long> mealIds,
                       Long clientId,
                       String opinion,
                       int rate,
                       OrderStatus orderStatus,
                       String startingAddress,
                       String destinationAddress) {
}
