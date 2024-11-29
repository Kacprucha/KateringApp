package com.kateringapp.backend.dtos;


import com.kateringapp.backend.entities.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MealGetDTO {

    Long mealId;

    String name;

    BigDecimal price;

    String description;

    byte[] photo;

    List<Ingredient> ingredients;

    UUID cateringFirmId;

    String cateringFirmName;

}
