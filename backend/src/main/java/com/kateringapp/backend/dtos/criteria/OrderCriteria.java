package com.kateringapp.backend.dtos.criteria;

import com.kateringapp.backend.entities.order.OrderStatus;

public record OrderCriteria(Integer minRate,
                            Integer maxRate,
                            OrderStatus orderStatus,
                            String startingAddress,
                            String destinationAddress) {
}
