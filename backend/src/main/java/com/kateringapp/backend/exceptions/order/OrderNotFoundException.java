package com.kateringapp.backend.exceptions.order;

import com.kateringapp.backend.exceptions.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order with id %d was not found"
                .formatted(id));
    }
}
