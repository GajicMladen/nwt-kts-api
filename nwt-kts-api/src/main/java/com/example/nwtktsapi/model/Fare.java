package com.example.nwtktsapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToMany
    @JoinTable(
            name = "stops",
            joinColumns = @JoinColumn(name = "fare_id"),
            inverseJoinColumns = @JoinColumn(name = "coordinates_id"))
    private List<Coordinates> stops;

    @OneToMany(mappedBy = "fare")
    private List<Payment> payments;

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
    private boolean calculateShortest;

    @Column(name = "is_reservation")
    private boolean isReservation;

    @Column(name = "distance")
    private float accepted;

    @Column(name = "estimated_time_left")
    private float estimatedTimeLeft;

    public Fare() {
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

    public List<Coordinates> getStops() {
        return stops;
    }

    public void setStops(List<Coordinates> stops) {
        this.stops = stops;
    }

    public List<Payment> getPayments() {
        return payments;
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

    public float getAccepted() {
        return accepted;
    }

    public void setAccepted(float accepted) {
        this.accepted = accepted;
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
}
