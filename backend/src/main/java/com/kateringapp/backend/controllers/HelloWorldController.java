package com.kateringapp.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloWorld")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/secured")
    @ResponseStatus(HttpStatus.OK)
    public String secured() {
        return "This is a secured endpoint. You are authenticated.";
    }
}
