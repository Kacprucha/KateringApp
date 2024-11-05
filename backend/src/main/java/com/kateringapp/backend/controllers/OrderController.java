package com.kateringapp.backend.controllers;

import com.kateringapp.backend.controllers.interfaces.IOrders;
import com.kateringapp.backend.dtos.OrderDTO;
import com.kateringapp.backend.dtos.criteria.OrderCriteria;
import com.kateringapp.backend.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements IOrders {

    private final IOrderService orderService;

    @Override
    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
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
    public List<OrderDTO> getOrders(@RequestParam(required = false) Integer minRate,
                                    @RequestParam(required = false) Integer maxRate) {
        return orderService.getOrders(minRate, maxRate);
    }

    @Override
    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable Long id,
                                @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }
}
