package com.kateringapp.backend.mappers;

import com.kateringapp.backend.dtos.AllergenGetDTO;
import com.kateringapp.backend.entities.Allergen;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllergenMapper {

    public AllergenGetDTO toAllergenGetDto(Allergen allergen) {
        return AllergenGetDTO.builder()
                .name(allergen.getName())
                .build();
    }
}
