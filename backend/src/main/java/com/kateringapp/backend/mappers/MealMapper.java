package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.CateringFirmData;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.mappers.interfaces.IMealMapper;
import org.springframework.stereotype.Component;

@Component
public class MealMapper implements IMealMapper {

    @Override
    public Meal mapDTOToEntity(MealCreateDTO mealDTO, CateringFirmData cateringFirmData) {
        return Meal.builder()
                .name(mealDTO.getName())
                .price(mealDTO.getPrice())
                .cateringFirmData(cateringFirmData)
                .description(mealDTO.getDescription())
                .photo(mealDTO.getPhoto())
                .ingredients(mealDTO.getIngredients())
                .build();
    }

    public MealGetDTO mapEntityToDTO(Meal meal) {
        return MealGetDTO.builder()
                .mealId(meal.getMealId())
                .description(meal.getDescription())
                .photo(meal.getPhoto())
                .price(meal.getPrice())
                .photo(meal.getPhoto())
                .name(meal.getName())
                .build();
    }
}
