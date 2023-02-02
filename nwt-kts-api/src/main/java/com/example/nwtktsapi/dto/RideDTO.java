package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Coordinate;
import com.example.nwtktsapi.model.Fare;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    private boolean reservation;
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<Long> deniedDrivers;


    private String pathForRide;

    public RideDTO() { }

    public RideDTO( Long driverId, Long clientId, String vehiclePlateNumber, String stops, String[] splitFare, int vehicleType, float price, int duration, float distance, boolean reservation, LocalDateTime startTime, LocalDateTime endTime, List<Long> deniedDrivers, String pathForRide) {
        this.driverId = driverId;
        this.clientId = clientId;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.stops = stops;
        this.splitFare = splitFare;
        this.vehicleType = vehicleType;
        this.price = price;
        this.duration = duration;
        this.distance = distance;
        this.reservation = reservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deniedDrivers = deniedDrivers;
        this.pathForRide = pathForRide;
    }

    public RideDTO(Fare fare) {
        this.rideId = fare.getFareID();
        this.driverId = fare.getDriver().getId();
        this.clientId = fare.getClients().get(0).getId();
        this.vehiclePlateNumber = fare.getDriver().getVehicle().getPlateNumber();
        this.stops = "";
        for (Coordinate cord: fare.getStops()) {
            this.stops = this.stops.concat(cord.getName()+","+
                    String.valueOf(cord.getLatitude())+","+
                    String.valueOf(cord.getLongitude())+";");
        }
//        this.splitFare =splitFare;
//        this.vehicleType = fare.getDriver().getVehicle().getType();
        this.price = fare.getPrice();
//        this.duration = (int)fare.getEstimatedTimeLeft();
        this.distance = fare.getDistance();
        this.reservation = fare.isReservation();
        this.startTime = fare.getStartTime();
        this.endTime = fare.getEndTime();
//        this.deniedDrivers = deniedDrivers;
//        this.pathForRide = pathForRide;
    }
    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public String getPathForRide() {
        return pathForRide;
    }

    public void setPathForRide(String pathForRide) {
        this.pathForRide = pathForRide;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    @JsonIgnore
    public List<Coordinate> getLocations() {
        List<Coordinate> locations = new ArrayList<>();
        for (String location : this.getStops().split(";")) {
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

    @Override
    public String toString() {
        return "RideDTO{" +
                "rideId=" + rideId +
                ", driverId=" + driverId +
                ", clientId=" + clientId +
                ", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", stops='" + stops + '\'' +
                ", splitFare=" + Arrays.toString(splitFare) +
                ", vehicleType=" + vehicleType +
                ", price=" + price +
                ", duration=" + duration +
                ", distance=" + distance +
                ", isReservation=" + reservation +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deniedDrivers=" + deniedDrivers +
                '}';
    }
}
