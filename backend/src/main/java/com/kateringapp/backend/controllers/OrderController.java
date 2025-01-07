package com.kateringapp.backend.controllers;

import com.kateringapp.backend.controllers.interfaces.IOrders;
import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.services.interfaces.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController implements IOrders {

    private final IOrderService orderService;

    @Override
    @Secured("ROLE_client")
    @PostMapping
    public OrderDTO createOrder(@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal Jwt token) {
        return orderService.createOrder(orderDTO, token);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @Override
    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @Override
    @GetMapping
    public List<OrderDTO> getOrders(
            @ParameterObject OrderCriteria orderCriteria, @AuthenticationPrincipal Jwt token) {
        return orderService.getOrders(orderCriteria, token);
    }

    @Override
    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id,
                                @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }
}
