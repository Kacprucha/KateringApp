package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.order.Order;
import com.kateringapp.backend.entities.order.OrderStatus;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.exceptions.order.OrderNotFoundException;
import com.kateringapp.backend.mappers.interfaces.IOrderMapper;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.MealRepository;
import com.kateringapp.backend.services.interfaces.IOrderService;
import com.kateringapp.backend.specifications.OrderSpecification;
import com.kateringapp.backend.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderMapper orderMapper;
    private final IOrderRepository orderRepository;
    private final MealRepository mealRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        Order order = orderMapper.mapDTOToEntity(orderDTO);
        order.setClientId(userId);

        order.setOrderStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        return orderMapper.mapEntityToDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return orderMapper
                .mapEntityToDTO(orderRepository.findById(id)
                        .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public List<OrderDTO> getOrders(OrderCriteria orderCriteria, Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        Specification<Order> orderSpecification = OrderSpecification.matchesCriteria(orderCriteria, userId);

        List<OrderDTO> orderDTOList = orderRepository.findAll(orderSpecification)
                .stream()
                .map(orderMapper::mapEntityToDTO)
                .toList();

        if(AuthHelper.isCateringFirm(jwt)) {
            removeUnnecessaryMeals(orderDTOList, userId);
        }

        return orderDTOList;
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Order updatedOrder = orderMapper.mapDTOToEntity(orderDTO);
        updatedOrder.setId(id);

        return orderMapper
                .mapEntityToDTO(orderRepository
                        .save(updatedOrder));
    }

    private void removeUnnecessaryMeals(List<OrderDTO> orderDTOS, UUID cateringFirmId) {
        orderDTOS.forEach(orderDTO -> {
            // Filter mealIds to retain only those belonging to the specified CateringFirm
            List<Long> filteredMealIds = orderDTO.getMealIds().stream()
                    .filter(mealId -> {
                        Meal meal = mealRepository.findById(mealId)
                                .orElseThrow(MealNotFoundException::new);
                        return meal.getCateringFirmData().getCateringFirmId().equals(cateringFirmId);
                    })
                    .toList();

            // Replace the existing mealIds with the filtered list
            orderDTO.setMealIds(filteredMealIds);
        });
    }
}
