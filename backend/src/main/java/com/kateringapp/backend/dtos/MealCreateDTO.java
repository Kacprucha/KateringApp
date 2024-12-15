package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MealCreateDTO {

    String name;

    BigDecimal price;

    String description;

    byte[] photo;

    List<String> ingredients;

}
