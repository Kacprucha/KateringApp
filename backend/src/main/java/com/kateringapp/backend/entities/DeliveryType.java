package com.kateringapp.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DeliveryType {

    @Id
    @GeneratedValue
    private Long deliveryTypeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryTypeEnum deliveryType;

    @JsonIgnore
    @ManyToMany(mappedBy = "deliveryOptions")
    private List<CateringFirmData> cateringFirmDataList;
}
