package com.kateringapp.backend.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Builder
public class MealCriteria {

    Long orderId;
    
    List<String> ingredients;

    List<String> allergens;

    Sort.Direction sortOrder;

    String sortBy;

    Integer pageSize;

    Integer pageNumber;

}
