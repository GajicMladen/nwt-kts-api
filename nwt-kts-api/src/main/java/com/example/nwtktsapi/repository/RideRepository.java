package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<Fare, Long> {

    @Query("select f from Fare f where f.isActive = true and f.driver.id = ?1")
    Fare getCurrentDriverFare(Long driverId);

    @Query("select f from Fare f where f.driver.id = ?1")
    List<Fare> getDriversFaresForDay(Long driverId);

    List<Fare> findByDriver_IdAndStartTimeBetween(Long driverId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT f FROM Fare f JOIN f.clients c WHERE c.id = :clientId AND f.startTime BETWEEN :startTime AND :endTime")
    List<Fare> findFaresByClientIdAndStartTimeBetween(@Param("clientId") Long clientId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
