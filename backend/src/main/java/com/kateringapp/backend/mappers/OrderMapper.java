package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper implements IOrderMapper {

    private final MealRepository mealRepository;

    @Override
    public OrderDTO mapEntityToDTO(Order order) {
        List<Long> meals = order.getMeals()
                .stream().map(Meal::getMealId)
                .toList();

        return OrderDTO.builder()
                .orderId(order.getId())
                .opinion(order.getOpinion())
                .orderStatus(order.getOrderStatus())
                .rate(order.getRate())
                .totalPrice(order.getTotalPrice())
                .mealIds(meals)
                .paymentMethod(order.getPaymentMethod())
                .startingAddress(order.getStartingAddress())
                .contactData(order.getContactData())
                .build();
    }

    @Override
    public Order mapDTOToEntity(OrderDTO orderDTO) {
        List<Meal> meals = mealRepository.findAllById(orderDTO.getMealIds());

        Map<Long, Long> countMap = orderDTO.getMealIds()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Meal> orderedMeals = meals.stream()
                .flatMap(element -> Collections.nCopies(countMap.get(element.getMealId()).intValue(),
                        element).stream())
                .collect(Collectors.toList());

        orderDTO.getContactData().setOrderDateTime(LocalDateTime.now());
        orderDTO.getContactData().setDueDateTime(LocalDateTime.now().plusHours(1));
        BigDecimal totalPrice = orderedMeals
                .stream()
                .map(Meal::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .rate(orderDTO.getRate())
                .opinion(orderDTO.getOpinion())
                .rate(orderDTO.getRate())
                .orderStatus(orderDTO.getOrderStatus())
                .startingAddress(orderDTO.getStartingAddress())
                .totalPrice(totalPrice)
                .meals(orderedMeals)
                .paymentMethod(orderDTO.getPaymentMethod())
                .contactData(orderDTO.getContactData())
                .build();

        if (orderDTO.getOrderStatus() == OrderStatus.COMPLETED) {
            order.setCompletedAt(Timestamp.from(Instant.now()));
        }

        return order;
    }

}
