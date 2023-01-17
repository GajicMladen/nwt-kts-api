package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Coordinate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class RideDTO {

    private Long rideId;
    private Long driverId;

    private Long clientId;
    private String vehiclePlateNumber;
    private String stops;
    private String[] splitFare;
    private int vehicleType;
    private float price;
    private int duration;
    private float distance;
    private boolean isReservation;

    private List<Long> deniedDrivers;


    public RideDTO() { }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public List<Long> getDeniedDrivers() {
        return deniedDrivers;
    }

    public void setDeniedDrivers(List<Long> deniedDrivers) {
        this.deniedDrivers = deniedDrivers;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isReservation() {
        return isReservation;
    }

    public void setReservation(boolean reservation) {
        isReservation = reservation;
    }

    @JsonIgnore
    public List<Coordinate> getLocations() {
        List<Coordinate> locations = new ArrayList<>();
        for (String location : getStops().split(";")) {
            String[] tokens = location.split(",");
            locations.add(new Coordinate(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
        }
        return locations;
    }
    @JsonIgnore
    public List<String> getLocationsNames() {
        List<String> locations = new ArrayList<>();
        for(Coordinate location : getLocations()) {
            locations.add(location.getName());
        }
        return locations;
    }
}
