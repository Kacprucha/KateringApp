package com.kateringapp.backend.services;


import com.kateringapp.backend.dtos.MealDTO;


public interface IMeals {

    MealDTO createMeal(MealDTO meal);

    MealDTO updateMeal(int id, MealDTO meal);

    MealDTO getMeal(int id);

    void deleteMeal(int id);

}
