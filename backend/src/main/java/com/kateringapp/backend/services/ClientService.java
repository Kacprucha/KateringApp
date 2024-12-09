package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.ClientCreateDTO;
import com.kateringapp.backend.dtos.ClientGetDTO;
import com.kateringapp.backend.entities.client.Client;
import com.kateringapp.backend.exceptions.client.ClientNotFoundException;
import com.kateringapp.backend.mappers.ClientMapper;
import com.kateringapp.backend.repositories.IClientRepository;
import com.kateringapp.backend.services.interfaces.IClientService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

  private final IClientRepository clientRepository;
  private final ClientMapper clientMapper;

  public ClientGetDTO getClient(Jwt token) {
    UUID userId = UUID.fromString(token.getSubject());

    return clientMapper
        .mapEntityToDTO(clientRepository.findById(userId)
            .orElseThrow(() -> new ClientNotFoundException(userId)));
  }

  public ClientGetDTO updateClient(ClientCreateDTO clientDTO, Jwt token) {
    UUID userId = UUID.fromString(token.getSubject());

    Client client = clientMapper.mapDTOToEntity(clientDTO);
    client.setClientId(userId);

    return clientMapper.mapEntityToDTO(clientRepository.save(client));

  }

}
