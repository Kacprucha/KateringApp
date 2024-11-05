package com.kateringapp.backend.dtos;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class MealDTO {

    int mealId;

    String name;

    int price;

    String description;

    String photoUrl;

}
