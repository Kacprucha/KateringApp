package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IngredientGetDTO {

    private String name;
    private List<AllergenGetDTO> allergens;
}
