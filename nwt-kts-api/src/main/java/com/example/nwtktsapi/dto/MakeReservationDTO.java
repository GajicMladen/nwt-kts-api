package com.example.nwtktsapi.dto;

import java.time.LocalDateTime;

public class MakeReservationDTO {
    private Long rideId;
    private LocalDateTime time;

    public MakeReservationDTO() {
    }


    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MakeReservationDTO{" +
                "rideId='" + rideId + '\'' +
                ", time=" + time +
                '}';
    }
}
