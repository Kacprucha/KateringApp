package com.kateringapp.backend.controllers;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealCriteria;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.services.MealsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meal")
public class MealsController {

    private final MealsService mealsService;

    @PostMapping
    public MealGetDTO createMeal(@RequestBody MealCreateDTO mealCreateDTO){
        return mealsService.createMeal(mealCreateDTO);
    }

    @PutMapping("/{id}")
    public MealGetDTO updateMeal(@PathVariable Long id, @RequestBody MealCreateDTO mealCreateDTO){
        return mealsService.updateMeal(id, mealCreateDTO);
    }

    @GetMapping("/{id}")
    public MealGetDTO getMeal(@PathVariable Long id){
        return mealsService.getMeal(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable Long id){
        mealsService.deleteMeal(id);
    }

    @GetMapping
    public List<MealGetDTO> getAllMeals(@ModelAttribute MealCriteria mealCriteria){
        return mealsService.getMeals(mealCriteria);
    }

}
