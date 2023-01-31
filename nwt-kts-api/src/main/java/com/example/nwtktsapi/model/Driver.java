package com.example.nwtktsapi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Driver extends  User{

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "position_id")
    private Coordinate position;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vehicle_id")
    @JsonManagedReference
    private Vehicle vehicle;

    @JsonIgnore
    @OneToMany(mappedBy = "offender")
    private List<Note> blamedNotes;

    @JsonIgnore
    @Column(name = "driver_status")
    private DriverStatus driverStatus;

    public Driver() {
    }

    public Driver(Long id, String password, String email, String name, String lastName, String town, String phone, String profilePhoto, boolean active, boolean blocked, float tokens, List<Note> notes, List<Message> messages, List<Role> roles, boolean inRide) {
        super(id, password, email, name, lastName, town, phone, profilePhoto, active, blocked, tokens, notes, messages, roles, inRide);
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
