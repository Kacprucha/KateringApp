package com.kateringapp.backend.repositories;

import com.kateringapp.backend.entities.CateringFirmData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CateringFirmDataRepository extends JpaRepository<CateringFirmData, UUID> {
    CateringFirmData findByCateringFirmId(UUID cateringFirmId);
}