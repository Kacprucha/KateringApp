package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealCriteria;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.*;
import com.kateringapp.backend.exceptions.BadRequestException;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.mappers.MealMapper;
import com.kateringapp.backend.repositories.CateringFirmDataRepository;
import com.kateringapp.backend.repositories.MealRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealsService implements IMeals{

    private final MealMapper mealMapper;
    private final CateringFirmDataRepository cateringFirmDataRepository;
    private final MealRepository mealRepository;
    @PersistenceContext
    private final EntityManager entityManager;

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
        Meal mealToUpdate = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));
        CateringFirmData cateringFirmData = mealToUpdate.getCateringFirmData();

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

    @Override
    public List<MealGetDTO> getMeals(MealCriteria mealCriteria){
        PathBuilder<Meal> pathBuilder = new PathBuilder<>(Meal.class, "meal");

        Querydsl querydsl = new Querydsl(entityManager, pathBuilder);

        Pageable pageRequest = PageRequest.of(mealCriteria.getPageNumber() == null ? 0 : mealCriteria.getPageNumber(),
                mealCriteria.getPageSize() == null ? 10 : mealCriteria.getPageSize());

        JPAQuery<Meal> query = queryCreatorForGetMeal(mealCriteria, pathBuilder);

        List<Meal> meals = querydsl.applyPagination(pageRequest, query).fetch();

        return meals.stream()
                .map(mealMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    private OrderSpecifier<?> createOrderSpecifier(Sort.Direction sortOrder, String sortBy, PathBuilder<Meal> pathBuilder) {

        if (sortBy == null) {
            return null;
        }

        if(!(sortBy.equals("price") || sortBy.equals("name"))){
            throw new BadRequestException("SortBy must be either 'price' or 'name'");
        }

        OrderSpecifier<?> orderSpecifier;

        switch (sortOrder) {
            case ASC -> orderSpecifier = pathBuilder.getString(sortBy).asc();
            case DESC ->  orderSpecifier = pathBuilder.getString(sortBy).desc();
            case null -> orderSpecifier = null;
        }

        return orderSpecifier;
    }

    private JPAQuery<Meal> queryCreatorForGetMeal(MealCriteria mealCriteria, PathBuilder<Meal> pathBuilder) {

        QMeal qMeal = QMeal.meal;
        QAllergen qAllergen = QAllergen.allergen;
        QIngredient qIngredient = QIngredient.ingredient;

        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(mealCriteria.getSortOrder(),
                mealCriteria.getSortBy(), pathBuilder);

        JPAQuery<Meal> query = new JPAQuery<>(entityManager).select(qMeal)
                .from(qMeal)
                .leftJoin(qMeal.ingredients, qIngredient)
                .leftJoin(qIngredient.allergens, qAllergen)
                .groupBy(qMeal.mealId);

        if (mealCriteria.getIngredients() != null && !mealCriteria.getIngredients().isEmpty()) {
            query.where(qIngredient.name.in(mealCriteria.getIngredients()))
                    .having(Expressions.numberTemplate(Long.class, "count(distinct {0})", qIngredient)
                            .eq((long) mealCriteria.getIngredients().size()));
        }

        if (mealCriteria.getAllergens() != null && !mealCriteria.getAllergens().isEmpty()) {
            query.where(qMeal.mealId.notIn(
                    JPAExpressions.select(qMeal.mealId)
                            .from(qMeal)
                            .leftJoin(qMeal.ingredients, qIngredient)
                            .leftJoin(qIngredient.allergens, qAllergen)
                            .where(qAllergen.name.in(mealCriteria.getAllergens()))
            ));
        }

        if(orderSpecifier != null) {
            query.orderBy(orderSpecifier);
        }

        return query;
    }

}
