package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CateringFirmDTO {

    int cateringFirmId;

    String name;

    String info;

    String logo;

    List<String> deliveryOptions;

}
