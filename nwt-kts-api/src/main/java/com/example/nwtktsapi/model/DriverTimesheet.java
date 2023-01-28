package com.example.nwtktsapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DriverTimesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name = "type") // login or logout
    private String type;

    @Column(name = "time")
    private LocalDateTime time;

    public DriverTimesheet() {

    }

    public DriverTimesheet(Driver driver, String type, LocalDateTime time) {
        this.driver = driver;
        this.type = type;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
