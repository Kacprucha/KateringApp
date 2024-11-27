package com.kateringapp.backend.services;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealCriteria;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.*;
import com.kateringapp.backend.exceptions.BadRequestException;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.mappers.MealMapper;
import com.kateringapp.backend.repositories.CateringFirmDataRepository;
import com.kateringapp.backend.repositories.IOrderRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealsService implements IMeals{

    private final MealMapper mealMapper;
    private final CateringFirmDataRepository cateringFirmDataRepository;
    private final MealRepository mealRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    private final IOrderRepository orderRepository;

    @Override
    public MealGetDTO createMeal(MealCreateDTO mealCreateDTO) {
        UUID currentUserId = getCurrentUserIdFromJwt();
        CateringFirmData cateringFirmData =
                cateringFirmDataRepository.findByCateringFirmId(currentUserId);
        Meal meal = mealMapper.mapDTOToEntity(mealCreateDTO, cateringFirmData);
        meal = mealRepository.save(meal);

        return mealMapper.mapEntityToDTO(meal);
    }

    @Override
    public MealGetDTO updateMeal(Long id, MealCreateDTO meal) {
        Meal mealToUpdate = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));
        CateringFirmData cateringFirmData = mealToUpdate.getCateringFirmData();

        UUID currentUserId = getCurrentUserIdFromJwt();

        if(!currentUserId.equals(cateringFirmData.getCateringFirmId())){
            throw new BadRequestException("You can update only your meals");
        }

        Meal updatedMeal = mealMapper.mapDTOToEntity(meal, cateringFirmData);
        updatedMeal.setMealId(id);

        return mealMapper.mapEntityToDTO(mealRepository.save(updatedMeal));
    }

    @Override
    public MealGetDTO getMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));
        UUID currentUserId = getCurrentUserIdFromJwt();

        if(!currentUserId.equals(meal.getCateringFirmData().getCateringFirmId())) {
            throw new BadRequestException("You can only get your meals");
        }

        return mealMapper.mapEntityToDTO(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new MealNotFoundException(id));
        UUID currentUserId = getCurrentUserIdFromJwt();

        if(!currentUserId.equals(meal.getCateringFirmData().getCateringFirmId())) {
            throw new BadRequestException("You can delete only your meals");
        }

        if(!orderRepository.findOrdersByMealsContaining(meal).isEmpty()){
            throw new BadRequestException("This meal can't be deleted. There are remaining orders containing this meal.");
        }

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
        UUID currentUserId = getCurrentUserIdFromJwt();

        OrderSpecifier<?> orderSpecifier = createOrderSpecifier(mealCriteria.getSortOrder(),
                mealCriteria.getSortBy(), pathBuilder);

        JPAQuery<Meal> query = new JPAQuery<>(entityManager).select(qMeal)
                .from(qMeal)
                .where(qMeal.cateringFirmData.cateringFirmId.eq(currentUserId))
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

    public UUID getCurrentUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {
            return UUID.fromString(jwt.getClaimAsString("sub"));
        }

        return null;
    }

}
