package com.kateringapp.backend.entities.order;

import com.kateringapp.backend.entities.Meal;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String opinion;
    private int rate;
    private String startingAddress;
    private String destinationAddress;
    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "order_meals",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals = new ArrayList<>();

}
