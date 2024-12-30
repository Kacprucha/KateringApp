package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper implements IOrderMapper {

    private final MealRepository mealRepository;

    @Override
    public OrderDTO mapEntityToDTO(Order order) {
        List<Long> mealIds = order.getMeals().
                stream()
                .map(Meal::getMealId)
                .toList();


        return OrderDTO.builder()
                .id(order.getId())
                .opinion(order.getOpinion())
                .orderStatus(order.getOrderStatus())
                .rate(order.getRate())
                .totalPrice(order.getTotalPrice())
                .mealIds(mealIds)
                .startingAddress(order.getStartingAddress())
                .destinationAddress(order.getDestinationAddress())
                .contactData(order.getContactData())
                .build();
    }

    @Override
    public Order mapDTOToEntity(OrderDTO orderDTO) {
        List<Meal> meals = mealRepository.findAllById(orderDTO.getMealIds());

        if (orderDTO.getMealIds().size() != meals.size()){
            throw new MealNotFoundException();
        }

        orderDTO.getContactData().setOrderDateTime(LocalDateTime.now());
        orderDTO.getContactData().setDueDateTime(LocalDateTime.now().plusHours(1));
        BigDecimal totalPrice = meals
                .stream()
                .map(Meal::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .rate(orderDTO.getRate())
                .opinion(orderDTO.getOpinion())
                .rate(orderDTO.getRate())
                .orderStatus(orderDTO.getOrderStatus())
                .startingAddress(orderDTO.getStartingAddress())
                .destinationAddress(orderDTO.getDestinationAddress())
                .totalPrice(totalPrice)
                .meals(meals)
                .contactData(orderDTO.getContactData())
                .build();

        if(orderDTO.getOrderStatus() == OrderStatus.COMPLETED){
            order.setCompletedAt(Timestamp.from(Instant.now()));
        }

        return order;

    }

}
