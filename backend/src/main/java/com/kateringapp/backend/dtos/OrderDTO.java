package com.kateringapp.backend.dtos;

import com.kateringapp.backend.entities.order.ContactData;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.entities.order.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Setter
@Getter
public class OrderDTO {

    private Long orderId;
    @NotNull
    private List<Long> mealIds;
    private String opinion;
    private BigDecimal totalPrice;
    private int rate;
    private OrderStatus orderStatus;
    @NotNull
    private PaymentMethod paymentMethod;
    private String startingAddress;
    @NotNull
    private ContactData contactData;
}
