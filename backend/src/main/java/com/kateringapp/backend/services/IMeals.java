package com.kateringapp.backend.services;


import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;


public interface IMeals {

    MealGetDTO createMeal(MealCreateDTO meal);

    MealGetDTO updateMeal(Long id, MealCreateDTO meal);

    MealGetDTO getMeal(Long id);

    void deleteMeal(Long id);

}
