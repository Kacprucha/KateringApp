package com.kateringapp.backend.exceptions.payment;

import com.kateringapp.backend.exceptions.BadRequestException;

public class CannotCreatePaymentException extends BadRequestException {
    public CannotCreatePaymentException() {
        super("Cannot create payment");
    }
}
