package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    //@Query("select d from Driver d where d.active = true and d.driverStatus = 0")
    @Query("select d from Driver d inner join d.vehicle v where d.active = true and d.driverStatus = 0 and v.type = ?1")
    List<Driver> getAvailableDrivers(VehicleType type);

    //@Query("select d from Driver d where d.active = true and d.driverStatus = 2")
    @Query("select d from Driver d inner join d.vehicle v where d.active = true and d.driverStatus = 2 and v.type = ?1")
    List<Driver> getDrivingDrivers(VehicleType type);

}
