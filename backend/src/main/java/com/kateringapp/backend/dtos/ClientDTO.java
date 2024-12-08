package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ClientDTO {
  String firstName;
  String lastName;
  String phoneNumber;
  String address;
}