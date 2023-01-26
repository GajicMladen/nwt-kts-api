package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<Fare, Long> {

    @Query("select f from Fare f where f.isActive = true and f.driver.id = ?1")
    List<Fare> getCurrentDriverFare(Long driverId);

    @Query("select f from Fare f where f.isReservation = ?1")
    List<Fare> findByIsReservation(boolean isReservation);

    @Query("select f from Fare f where f.isReservation = ?1 and f.startTime > ?2")
    List<Fare> findByIsReservationAndStartTimeGreaterThan(boolean isReservation, LocalDateTime startTime);

    @Query("select f from Fare f where f.isReservation = ?1 and (f.startTime < ?2 or f.endTime > ?3) and f.isDone = false")
    List<Fare> getReservationsInPeriod(boolean isReservation, LocalDateTime startTime, LocalDateTime endTime);


    @Query("select f from Fare f where f.fareID = ?1")
    Fare findByFareID(Long fareID);







}
