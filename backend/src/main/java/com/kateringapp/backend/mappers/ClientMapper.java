package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.ClientCreateDTO;
import com.kateringapp.backend.dtos.ClientGetDTO;
import com.kateringapp.backend.entities.client.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

  public Client mapDTOToEntity(ClientCreateDTO clientCreateDTO) {
    return Client.builder()
        .firstName(clientCreateDTO.getFirstName())
        .lastName(clientCreateDTO.getLastName())
        .phoneNumber(clientCreateDTO.getPhoneNumber())
        .address(clientCreateDTO.getAddress())
        .build();
  }

  public ClientGetDTO mapEntityToDTO(Client client){
    return ClientGetDTO.builder()
        .clientId(client.getClientId())
        .firstName(client.getFirstName())
        .lastName(client.getLastName())
        .phoneNumber(client.getPhoneNumber())
        .address(client.getAddress())
        .build();
  }

}
