package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;

public class DriverChangeStatusDTO {

    private Long driverId;
    private String plateNumber;
    private DriverStatus driverStatus;

    public DriverChangeStatusDTO() {
    }

    public DriverChangeStatusDTO(Driver driver) {
        this.driverId = driver.getId();
        this.plateNumber = driver.getVehicle().getPlateNumber();
        this.driverStatus = driver.getDriverStatus();
    }

    public DriverChangeStatusDTO(Long driverId, String plateNumber, DriverStatus driverStatus) {
        this.driverId = driverId;
        this.plateNumber = plateNumber;
        this.driverStatus = driverStatus;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }
}
