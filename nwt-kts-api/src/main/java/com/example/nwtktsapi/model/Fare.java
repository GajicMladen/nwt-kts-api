package com.example.nwtktsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fare_id", nullable = false)
    private Long fareID;

    @ManyToMany
    @JoinTable(
            name = "clients_for_fare",
            joinColumns = @JoinColumn(name = "fare_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients;

    @Column(name="start_address")
    private String startAddress;
    
    @Column(name="end_address")
    private String endAddress;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "stops",
            joinColumns = @JoinColumn(name = "fare_id"),
            inverseJoinColumns = @JoinColumn(name = "coordinates_id"))
    private List<Coordinate> stops;

    @OneToMany(mappedBy = "fare")
    private List<Payment> payments;

    @OneToMany(mappedBy = "fare")
    private List<Rating> ratings;
    
    @OneToMany(mappedBy = "fare")
    private List<FavouriteRoute> favouriteRoutes;
    
    @Column(name = "price")
    private float price;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Column(name = "calculate_shortest")
    private Boolean calculateShortest;

    @Column(name = "is_reservation")
    private boolean isReservation;

    @Column(name = "distance")
    private float distance;

    @Column(name = "estimated_time_left")
    private Float estimatedTimeLeft;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_done")
    private boolean isDone;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "path_id", referencedColumnName = "id")
    private PathForRide pathForRide;

    public Fare() {
        setClients(new ArrayList<>());
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public PathForRide getPathForRide() {
        return pathForRide;
    }

    public void setPathForRide(PathForRide pathForRide) {
        this.pathForRide = pathForRide;
    }

    public Long getFareID() {
        return fareID;
    }

    public void setFareID(Long fareID) {
        this.fareID = fareID;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Coordinate> getStops() {
        return stops;
    }

    public void setStops(List<Coordinate> stops) {
        this.stops = stops;
    }
    
    public String getStartAddress() {
		return startAddress;
	}

    public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

    public String getEndAddress() {
		return endAddress;
	}

    public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public List<Payment> getPayments() {
        return payments;
    }
	
    public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isCalculateShortest() {
        return calculateShortest;
    }

    public void setCalculateShortest(boolean calculateShortest) {
        this.calculateShortest = calculateShortest;
    }

    public boolean isReservation() {
        return isReservation;
    }

    public void setReservation(boolean reservation) {
        isReservation = reservation;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getEstimatedTimeLeft() {
        return estimatedTimeLeft;
    }

    public void setEstimatedTimeLeft(float estimatedTimeLeft) {
        this.estimatedTimeLeft = estimatedTimeLeft;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getId() {
        return fareID;
    }

    public void setId(Long id) {
        this.fareID = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
