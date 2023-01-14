package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RideRepository extends JpaRepository<Fare, Long> {

    @Query("select f from Fare f where f.isActive = true and f.driver.id = ?1")
    Fare getCurrentDriverFare(Long driverId);
}
