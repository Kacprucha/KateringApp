package com.kateringapp.backend.exceptions.client;

import com.kateringapp.backend.exceptions.NotFoundException;
import java.util.UUID;

public class ClientNotFoundException extends NotFoundException {

  public ClientNotFoundException(UUID id) {
    super("Client with id " + id + " was not found");
  }
}
