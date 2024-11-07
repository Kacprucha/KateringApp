package com.kateringapp.backend.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Allergen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long allergenId;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "allergens")
    private List<Ingredient> ingredients;

}
