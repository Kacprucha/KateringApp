package com.kateringapp.backend.handlers;

import com.kateringapp.backend.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public final ResponseEntity<String> badRequestHandler(BadRequestException exception) {
        String message = exception.getMessage();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}

