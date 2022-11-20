package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_user_id")
    private Driver driver;

    @Column(name = "name")
    private String name;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "type")
    private VehicleType type;

    public Vehicle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
