package com.kateringapp.backend;

import com.kateringapp.backend.configurations.SpringContextRetriever;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
fjewifm'ewjfnewfewf J K3[

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MealsUnitTest {

    @Mock
    private MealMapper mealMapper;

    @Mock
    private CateringFirmDataRepository cateringFirmDataRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private IOrderRepository orderRepository;

    @InjectMocks
    private MealsService mealsService;

    @Mock
    private SpringContextRetriever springContextRetriever;

    private Meal meal;
    private MealCreateDTO mealCreateDTO;
    private CateringFirmData cateringFirmData;
    private UUID cateringFirmId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cateringFirmId = UUID.fromString("6c84fb95-12c4-11ec-82a8-0242ac130007");

        cateringFirmData = CateringFirmData.builder()
                .cateringFirmId(cateringFirmId)
                .name("Test Catering Firm")
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
                .build();

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn(cateringFirmId.toString());

        when(springContextRetriever.getCurrentUserIdFromJwt()).thenReturn(cateringFirmId);

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createMeal_ShouldCreateMealSuccessfully() {
        when(cateringFirmDataRepository.findByCateringFirmId(cateringFirmId)).thenReturn(cateringFirmData);
        when(mealMapper.mapDTOToEntity(mealCreateDTO, cateringFirmData)).thenReturn(meal);
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .cateringFirmId(cateringFirmId)
                .build());

        MealGetDTO result = mealsService.createMeal(mealCreateDTO);

        assertNotNull(result);
        assertEquals("Sample Meal", result.getName());
        assertEquals(cateringFirmId, result.getCateringFirmId());

        verify(mealRepository, times(1)).save(meal);
    }


    @Test
    void updateMeal_ShouldUpdateMealSuccessfully() {
        MealCreateDTO updatedMealDTO = MealCreateDTO.builder()
                .name("Updated Meal")
                .price(BigDecimal.valueOf(150))
                .description("Updated description")
                .build();

        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(mealMapper.mapDTOToEntity(updatedMealDTO, cateringFirmData)).thenReturn(meal);
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Updated Meal")
                .price(BigDecimal.valueOf(150))
                .description("Updated description")
                .cateringFirmId(cateringFirmId)
                .build());

        MealGetDTO result = mealsService.updateMeal(1L, updatedMealDTO);

        assertNotNull(result);
        assertEquals("Updated Meal", result.getName());
        assertEquals(BigDecimal.valueOf(150), result.getPrice());

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
    void getMeal_ShouldReturnMealSuccessfully() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(mealMapper.mapEntityToDTO(meal)).thenReturn(MealGetDTO.builder()
                .mealId(1L)
                .name("Sample Meal")
                .price(BigDecimal.valueOf(100))
                .description("Delicious sample meal")
                .cateringFirmId(cateringFirmId)
                .build());

        MealGetDTO result = mealsService.getMeal(1L);

        assertNotNull(result);
        assertEquals("Sample Meal", result.getName());
    }


    @Test
    void deleteMeal_ShouldDeleteMealSuccessfully() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));
        when(orderRepository.findOrdersByMealsContaining(meal)).thenReturn(Collections.emptyList());

        mealsService.deleteMeal(1L);

        verify(mealRepository, times(1)).delete(meal);
    }

    @Test
    void deleteMealNotFound() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MealNotFoundException.class, () -> mealsService.deleteMeal(1L));
    }

}
