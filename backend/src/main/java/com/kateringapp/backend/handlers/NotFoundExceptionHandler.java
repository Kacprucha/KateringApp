package com.kateringapp.backend.handlers;

import com.kateringapp.backend.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<String> notFoundHandler(NotFoundException exception) {
        String message = exception.getMessage();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
