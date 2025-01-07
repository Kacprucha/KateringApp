package com.kateringapp.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MealStatisticsDTO {
    private MealGetDTO mealDetails;
    private Long quantitySold;
    private BigDecimal totalSalesValue;
}

