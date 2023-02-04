package com.example.nwtktsapi.dto;

public class NewRideRequestDriverDTO {

    private String driverId;
    private RideDTO rideDTO;

    public NewRideRequestDriverDTO() {
    }

    public NewRideRequestDriverDTO(String driverId, RideDTO rideDTO) {
        this.driverId = driverId;
        this.rideDTO = rideDTO;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public RideDTO getRideDTO() {
        return rideDTO;
    }

    public void setRideDTO(RideDTO rideDTO) {
        this.rideDTO = rideDTO;
    }
}
