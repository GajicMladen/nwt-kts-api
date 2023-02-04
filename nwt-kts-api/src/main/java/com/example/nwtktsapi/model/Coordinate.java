package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinates_id", nullable = false)
    private Long id;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "stop_number")
    private Integer stopNumber;

    @Column(name = "name")
    private String name;

    public Coordinate() {
    }

    public Coordinate(double latitude, double longitude, int stopNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopNumber = stopNumber;
    }

    public Coordinate(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double x) {
        this.latitude = x;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double y) {
        this.longitude = y;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
