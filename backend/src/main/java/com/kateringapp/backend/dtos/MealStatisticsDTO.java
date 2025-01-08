package com.kateringapp.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class MealStatisticsDTO {
    Long mealId;
    String name;
    BigDecimal price;
    String description;
    byte[] photo;
    UUID cateringFirmId;
    String cateringFirmName;
    private Long quantitySold;
    private BigDecimal totalSalesValue;
}

