package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinates_id", nullable = false)
    private Long id;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "stop_number")
    private Integer stopNumber;

    public Coordinates() {
    }

    public Coordinates(float latitude, float longitude, int stopNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopNumber = stopNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float x) {
        this.latitude = x;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float y) {
        this.longitude = y;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

}
