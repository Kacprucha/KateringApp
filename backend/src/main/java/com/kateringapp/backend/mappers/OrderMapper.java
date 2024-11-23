package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                .mealIds(mealIds)
                .startingAddress(order.getStartingAddress())
                .destinationAddress(order.getDestinationAddress())
                .build();
    }

    @Override
    public Order mapDTOToEntity(OrderDTO orderDTO) {
        List<Meal> meals = mealRepository.findAllById(orderDTO.mealIds());

        if (orderDTO.mealIds().size() != meals.size()){
            throw new MealNotFoundException();
        }

        return Order.builder()
                .rate(orderDTO.rate())
                .opinion(orderDTO.opinion())
                .rate(orderDTO.rate())
                .startingAddress(orderDTO.startingAddress())
                .destinationAddress(orderDTO.destinationAddress())
                .meals(meals)
                .build();
    }

}
