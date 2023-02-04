package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.DriverTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverTimesheetRepository extends JpaRepository<DriverTimesheet, Long> {

    @Query("select dt from DriverTimesheet dt where dt.driver.id = :driverId and dt.time between :startTime and :endTime")
    List<DriverTimesheet> findByDriverIdAndTimeBetween(@Param("driverId") Long driverId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
