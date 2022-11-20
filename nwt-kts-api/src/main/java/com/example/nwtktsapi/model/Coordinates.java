package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinates_id", nullable = false)
    private Long id;
    @Column(name = "x")
    private float x;
    @Column(name = "y")
    private float y;
    @Column(name = "stop_number")
    private int stopNumber;

    public Coordinates() {
    }

    public Coordinates(float x, float y, int stopNumber) {
        this.x = x;
        this.y = y;
        this.stopNumber = stopNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

}
