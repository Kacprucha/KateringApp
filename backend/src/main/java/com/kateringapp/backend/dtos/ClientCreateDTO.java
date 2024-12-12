package com.kateringapp.backend.dtos;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Value
@SuperBuilder
@Jacksonized
public class ClientCreateDTO{
  String firstName;
  String lastName;
  String email;
  String phoneNumber;
  String address;
}