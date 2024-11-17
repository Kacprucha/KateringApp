package com.kateringapp.backend.mappers.interfaces;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.CateringFirmData;
import com.kateringapp.backend.entities.Meal;

public interface IMealMapper {

    MealGetDTO mapEntityToDTO(Meal meal);

    Meal mapDTOToEntity(MealCreateDTO mealDTO, CateringFirmData cateringFirmData);
}
