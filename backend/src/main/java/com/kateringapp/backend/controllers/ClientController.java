package com.kateringapp.backend.controllers;

import com.kateringapp.backend.controllers.interfaces.IClient;
import com.kateringapp.backend.dtos.ClientCreateDTO;

import com.kateringapp.backend.dtos.ClientGetDTO;

import com.kateringapp.backend.services.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController implements IClient {

  private final IClientService clientService;

  @PutMapping
  public ClientGetDTO updateClient(@RequestBody ClientCreateDTO clientDTO, @AuthenticationPrincipal Jwt token) {
    return clientService.updateClient(clientDTO, token);
  }

  @GetMapping
  public ResponseEntity<Object> getClient(@AuthenticationPrincipal Jwt token) {
    ClientGetDTO clientGetDTO = clientService.getClient(token);
    Object response = clientGetDTO!=null ? clientGetDTO : "null";
    return ResponseEntity.ok(response);
  }
}
