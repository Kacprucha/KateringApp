package com.kateringapp.backend.controllers;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/secured/roles")
    public String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(", "));

        return "User roles: " + roles;
    }

    @Secured("ROLE_client")
    @GetMapping("/secured/client")
    @ResponseStatus(HttpStatus.OK)
    public String securedClient(){ return "This is a secured endpoint only for client"; }


    @Secured("ROLE_catering-firm")
    @GetMapping("/secured/catering-firm")
    @ResponseStatus(HttpStatus.OK)
    public String securedCateringFirm(){ return "This is a secured endpoint only for catering-firm"; }
}
