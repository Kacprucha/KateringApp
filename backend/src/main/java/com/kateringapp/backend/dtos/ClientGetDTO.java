package com.kateringapp.backend.dtos;

import java.util.UUID;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
public class ClientGetDTO {
  UUID clientId;
  String firstName;
  String lastName;
  String email;
  String phoneNumber;
  String address;
}
