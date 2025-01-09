package com.kateringapp.backend.exceptions.order;

import com.kateringapp.backend.exceptions.BadRequestException;

public class ForbiddenOrderStatusUpdateException extends BadRequestException {
    public ForbiddenOrderStatusUpdateException(String message) {
        super(message);
    }
}
