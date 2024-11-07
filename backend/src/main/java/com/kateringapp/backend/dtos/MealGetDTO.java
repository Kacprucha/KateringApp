package com.kateringapp.backend.dtos;


import com.kateringapp.backend.entities.Ingredient;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class MealGetDTO {

    Long mealId;

    String name;

    int price;

    String description;

    String photo;

    List<Ingredient> ingredients;

    CateringFirmGetDTO cateringFirm;

}
