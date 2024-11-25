package com.kateringapp.backend;

import com.kateringapp.backend.dtos.MealCreateDTO;
import com.kateringapp.backend.dtos.MealGetDTO;
import com.kateringapp.backend.entities.Meal;
import com.kateringapp.backend.entities.CateringFirmData;
import com.kateringapp.backend.repositories.IOrderRepository;
import com.kateringapp.backend.repositories.MealRepository;
import com.kateringapp.backend.repositories.CateringFirmDataRepository;
import com.kateringapp.backend.mappers.MealMapper;
import com.kateringapp.backend.exceptions.meal.MealNotFoundException;
import com.kateringapp.backend.services.MealsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MealsUnitTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private CateringFirmDataRepository cateringFirmDataRepository;

    @Mock
    private MealMapper mealMapper;

    @InjectMocks
    private MealsService mealsService;

    private Meal meal;
    private MealCreateDTO mealCreateDTO;
    private CateringFirmData cateringFirmData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cateringFirmData = CateringFirmData.builder()
                .cateringFirmId(1L)
                .build();

        meal = Meal.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .cateringFirmData(cateringFirmData)
                .build();

        mealCreateDTO = MealCreateDTO.builder()
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .cateringFirmId(1L)
                .build();
    }

    @Test
    void createMeal() {
        when(cateringFirmDataRepository.findByCateringFirmId(1L)).thenReturn(cateringFirmData);
        when(mealMapper.mapDTOToEntity(mealCreateDTO, cateringFirmData)).thenReturn(meal);
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .photo(null)
                .build());

        MealGetDTO mealGetDTO = mealsService.createMeal(mealCreateDTO);

        assertNotNull(mealGetDTO);
        assertEquals("Sample Meal", mealGetDTO.getName());
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void updateMeal() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(cateringFirmDataRepository.findById(1L)).thenReturn(Optional.of(cateringFirmData));
        when(mealMapper.mapDTOToEntity(mealCreateDTO, cateringFirmData)).thenReturn(meal);
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .photo(null)
                .build());

        MealGetDTO updatedMeal = mealsService.updateMeal(1L, mealCreateDTO);

        assertNotNull(updatedMeal);
        assertEquals("Sample Meal", updatedMeal.getName());
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void getMeal() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .photo(null)
                .build());

        MealGetDTO mealGetDTO = mealsService.getMeal(1L);

        assertNotNull(mealGetDTO);
        assertEquals("Sample Meal", mealGetDTO.getName());
        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void getMealNotFound() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MealNotFoundException.class, () -> mealsService.getMeal(1L));
    }

    @Test
    void deleteMeal() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(orderRepository.findOrdersByMealsContaining(meal)).thenReturn(null);

        mealsService.deleteMeal(1L);

        verify(mealRepository, times(1)).delete(meal);
    }

    @Test
    void deleteMealNotFound() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MealNotFoundException.class, () -> mealsService.deleteMeal(1L));
    }

}
