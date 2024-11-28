package com.kateringapp.backend.services;


import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealCriteria;
import com.kateringapp.backend.dtos.MealGetDTO;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;


public interface IMeals {

    MealGetDTO createMeal(MealCreateDTO meal);

    MealGetDTO updateMeal(Long id, MealCreateDTO meal);

    MealGetDTO getMeal(Long id);

    List<MealGetDTO> getMeals(MealCriteria mealCriteria, Jwt jwt);

    void deleteMeal(Long id);

}
