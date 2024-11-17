package com.kateringapp.backend.exceptions.meal;

import com.kateringapp.backend.exceptions.NotFoundException;

public class MealNotFoundException extends NotFoundException {
    public MealNotFoundException(Long id) {
      super("Meal with id " + id + " was not found");
    }
}
