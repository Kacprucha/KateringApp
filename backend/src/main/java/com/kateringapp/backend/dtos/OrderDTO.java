package com.kateringapp.backend.dtos;

import com.kateringapp.backend.entities.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDTO(Long id,
                       @NotNull
                       List<Long> mealIds,
                       Long clientId,
                       String opinion,
                       BigDecimal totalPrice,
                       int rate,
                       OrderStatus orderStatus,
                       String startingAddress,
                       String destinationAddress) {
}
