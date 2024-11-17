package com.kateringapp.backend.dtos;


import com.kateringapp.backend.entities.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MealGetDTO {

    Long mealId;

    String name;

    BigDecimal price;

    String description;

    byte[] photo;

    List<Ingredient> ingredients;

}
