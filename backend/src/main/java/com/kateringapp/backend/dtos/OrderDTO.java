package com.kateringapp.backend.dtos;

import java.util.List;

public record OrderDTO(List<Long> mealIds,
                       Long clientId,
                       String opinion,
                       int rate,
                       String startingAddress,
                       String destinationAddress) {
}
