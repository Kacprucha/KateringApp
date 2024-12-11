package com.kateringapp.backend.repositories;

import com.kateringapp.backend.entities.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameIn(List<String> names);

    @Query("SELECT i FROM Ingredient i JOIN i.meals m WHERE m.mealId = :mealId")
    Page<Ingredient> findByMeals(@Param("mealId") Long mealId, Pageable pageable);
}