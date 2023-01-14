package com.example.nwtktsapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver extends  User{

    @OneToOne
    @JoinColumn(name = "position_id")
    private Coordinate position;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "offender")
    private List<Note> blamedNotes;

    @Column(name = "driver_status")
    private DriverStatus driverStatus;

    public Driver() {
    }

    public List<Note> getBlamedMessages() {
        return blamedNotes;
    }

    public void setBlamedMessages(List<Note> blamedNotes) {
        this.blamedNotes = blamedNotes;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public void updatePosition( Coordinate newPosition){
        this.position.setLatitude( newPosition.getLatitude() );
        this.position.setLongitude( newPosition.getLongitude() );
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }


}
