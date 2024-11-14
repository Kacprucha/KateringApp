package com.kateringapp.backend.data;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;

import java.util.Collections;

public class TestDataProvider {

    public static OrderDTO provideOrderDTO() {
        return OrderDTO.builder()
                .id(1L)
                .rate(3)
                .orderStatus(OrderStatus.PENDING)
                .startingAddress("aaa")
                .destinationAddress("bbb")
                .opinion("good")
                .clientId(1L)
                .mealIds(Collections.emptyList())
                .build();
    }

    public static Order provideOrder() {
        return Order.builder()
                .id(1L)
                .rate(3)
                .orderStatus(OrderStatus.PENDING)
                .startingAddress("aaa")
                .destinationAddress("bbb")
                .opinion("good")
                .build();
    }

}
