package com.kateringapp.backend.entities;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CateringFirmData {

    @Id
    private UUID cateringFirmId;

    private String info;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "catering_firm_data_delivery_type",
            joinColumns = @JoinColumn(name = "catering_firm_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_type_id")
    )
    private List<DeliveryType> deliveryOptions;

    @Lob
    @Column(name = "logo", columnDefinition = "BYTEA")
    private byte[] logo;

    @OneToMany
    private List<Meal> meals;

}
