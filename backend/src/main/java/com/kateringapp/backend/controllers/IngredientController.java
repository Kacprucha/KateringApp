package com.kateringapp.backend.controllers;

import com.kateringapp.backend.dtos.IngredientGetDTO;
import com.kateringapp.backend.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<IngredientGetDTO> getIngredients(
            @PageableDefault(size = 10, page = 0, sort = "name") Pageable pageable,
            @RequestParam(required = false) Long mealId) {

        return ingredientService.getIngredients(pageable, mealId);
    }
}
