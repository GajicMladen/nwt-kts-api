package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class VehiclePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "vehicle_type")
    private VehicleType type;

    @Column(name = "price")
    private int price;

    public VehiclePrice() {
    }

    public VehiclePrice(Long id, VehicleType type, int price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
