package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.CateringFirmData;
import com.kateringapp.backend.entities.Ingredient;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.mappers.interfaces.IMealMapper;
import com.kateringapp.backend.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MealMapper implements IMealMapper {

    private final IngredientRepository ingredientRepository;

    @Override
    public Meal mapDTOToEntity(MealCreateDTO mealDTO, CateringFirmData cateringFirmData) {

        List<Ingredient> ingredients = ingredientRepository.findByNameIn(mealDTO.getIngredients());

        return Meal.builder()
                .name(mealDTO.getName())
                .price(mealDTO.getPrice())
                .cateringFirmData(cateringFirmData)
                .description(mealDTO.getDescription())
                .photo(mealDTO.getPhoto())
                .ingredients(ingredients)
                .build();
    }

    @Override
    public MealGetDTO mapEntityToDTO(Meal meal) {
        return MealGetDTO.builder()
                .mealId(meal.getMealId())
                .description(meal.getDescription())
                .photo(meal.getPhoto())
                .price(meal.getPrice())
                .name(meal.getName())
                .ingredients(meal.getIngredients())
                .cateringFirmId(meal.getCateringFirmData().getCateringFirmId())
                .cateringFirmName(meal.getCateringFirmData().getName())
                .build();
    }
}
