package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.VehiclePrice;
import com.example.nwtktsapi.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePriceRepository extends JpaRepository<VehiclePrice, Long> {
    VehiclePrice findByType(VehicleType type);
}
