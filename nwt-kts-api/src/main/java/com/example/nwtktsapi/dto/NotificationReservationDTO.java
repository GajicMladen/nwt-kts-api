package com.example.nwtktsapi.dto;

public class NotificationReservationDTO {

    private RideDTO rideDTO;

    private String message;

    public RideDTO getRideDTO() {
        return rideDTO;
    }

    public void setRideDTO(RideDTO rideDTO) {
        this.rideDTO = rideDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
