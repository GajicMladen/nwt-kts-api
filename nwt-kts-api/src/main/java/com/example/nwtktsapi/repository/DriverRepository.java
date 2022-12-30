package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select d from Driver d where d.active = true and d.driverStatus = 0")
    List<Driver> getAvailableDrivers();

    @Query("select d from Driver d where d.active = true and d.driverStatus = 2")
    List<Driver> getDrivingDrivers();
}
