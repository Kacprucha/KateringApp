package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.AllergenGetDTO;
import com.kateringapp.backend.dtos.IngredientGetDTO;
import com.kateringapp.backend.entities.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngredientMapper {

    private final AllergenMapper allergenMapper;

    public IngredientGetDTO toIngredientGetDTO(Ingredient ingredient) {

        List<AllergenGetDTO> allergenGetDTOS = ingredient.getAllergens().stream().map(
                allergenMapper::toAllergenGetDto
        ).toList();

        return IngredientGetDTO.builder()
                .name(ingredient.getName())
                .allergens(allergenGetDTOS)
                .build();
    }
}
