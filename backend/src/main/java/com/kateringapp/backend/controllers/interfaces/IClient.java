package com.kateringapp.backend.controllers.interfaces;

import com.kateringapp.backend.dtos.ClientCreateDTO;
import com.kateringapp.backend.dtos.ClientGetDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IClient {

  ClientGetDTO updateClient(ClientCreateDTO clientCreateDTO, Jwt jwt);
  ClientGetDTO getClient(Jwt jwt);

}
