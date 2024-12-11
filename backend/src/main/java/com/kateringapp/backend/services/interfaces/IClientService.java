package com.kateringapp.backend.services.interfaces;

import com.kateringapp.backend.dtos.ClientCreateDTO;
import com.kateringapp.backend.dtos.ClientGetDTO;
import org.springframework.security.oauth2.jwt.Jwt;

public interface IClientService {
  ClientGetDTO getClient(Jwt token);
  ClientGetDTO updateClient(ClientCreateDTO clientDTO, Jwt token);

}
