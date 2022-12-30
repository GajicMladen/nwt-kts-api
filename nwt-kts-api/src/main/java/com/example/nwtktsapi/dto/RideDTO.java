package com.example.nwtktsapi.dto;

import java.util.ArrayList;
import java.util.List;

public class RideDTO {

    private String stops;
    private String[] splitFare;
    private int vehicleType;
    private float price;
    private int duration;
    private boolean isReservation;

    public RideDTO() { }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public String[] getSplitFare() {
        return splitFare;
    }

    public void setSplitFare(String[] splitFare) {
        this.splitFare = splitFare;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isReservation() {
        return isReservation;
    }

    public void setReservation(boolean reservation) {
        isReservation = reservation;
    }

    public List<StationDTO> getLocations() {
        List<StationDTO> locations = new ArrayList<>();
        for (String location : getStops().split(";")) {
            String[] tokens = location.split(",");
            locations.add(new StationDTO(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
        }
        return locations;
    }

    public List<String> getLocationsNames() {
        List<String> locations = new ArrayList<>();
        for(StationDTO location : getLocations()) {
            locations.add(location.getName());
        }
        return locations;
    }
}
