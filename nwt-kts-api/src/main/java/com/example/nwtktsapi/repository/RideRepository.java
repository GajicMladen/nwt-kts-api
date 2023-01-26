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

    List<Fare> findByDriver_IdAndStartTimeBetween(Long driverId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("select f from Fare f join f.clients c where c.id = :clientId and f.startTime between :startTime and :endTime")
    List<Fare> findFaresByClientIdAndStartTimeBetween(@Param("clientId") Long clientId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("select f from Fare f where f.startTime between :startTime and :endTime")
    List<Fare> findByStartTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
