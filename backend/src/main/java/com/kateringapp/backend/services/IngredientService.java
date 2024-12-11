package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.IngredientGetDTO;
import com.kateringapp.backend.entities.Ingredient;
import com.kateringapp.backend.mappers.IngredientMapper;
import com.kateringapp.backend.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientGetDTO> getIngredients(Pageable pageable, Long mealId) {

        List<Ingredient> ingredients;

        if(mealId == null) {
            ingredients = ingredientRepository.findAll(pageable).getContent();
        } else {
            ingredients = ingredientRepository.findByMeals(mealId, pageable).getContent();
        }

        return ingredients.stream().map(
                ingredientMapper::toIngredientGetDTO
        ).toList();
    }
}
