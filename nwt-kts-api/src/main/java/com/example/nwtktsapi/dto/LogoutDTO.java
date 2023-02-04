package com.example.nwtktsapi.dto;

public class LogoutDTO {

    private String driverId;

    public LogoutDTO(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
