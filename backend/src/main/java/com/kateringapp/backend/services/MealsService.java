package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.CateringFirmData;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.mappers.MealMapper;
import com.kateringapp.backend.repositories.CateringFirmDataRepository;
import com.kateringapp.backend.repositories.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealsService implements IMeals{

    private final MealMapper mealMapper;
    private final CateringFirmDataRepository cateringFirmDataRepository;
    private final MealRepository mealRepository;

    @Override
    public MealGetDTO createMeal(MealCreateDTO mealCreateDTO) {

        CateringFirmData cateringFirmData =
                cateringFirmDataRepository.findByCateringFirmId(mealCreateDTO.getCateringFirmId());

        Meal meal = mealMapper.mapDTOToEntity(mealCreateDTO, cateringFirmData);

        meal = mealRepository.save(meal);

        return mealMapper.mapEntityToDTO(meal);
    }

    @Override
    public MealGetDTO updateMeal(Long id, MealCreateDTO meal) {
        mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));

        CateringFirmData cateringFirmData = cateringFirmDataRepository.findById(id)
                .orElseThrow(() -> new MealNotFoundException(id));

        Meal updatedMeal = mealMapper.mapDTOToEntity(meal, cateringFirmData);
        updatedMeal.setMealId(id);

        return mealMapper.mapEntityToDTO(mealRepository.save(updatedMeal));
    }

    @Override
    public MealGetDTO getMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealMapper.mapEntityToDTO(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));

        mealRepository.delete(meal);
    }
}
