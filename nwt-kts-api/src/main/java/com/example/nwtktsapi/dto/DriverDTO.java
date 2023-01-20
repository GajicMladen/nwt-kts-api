package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.VehicleType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.nwtktsapi.model.Driver} entity
 */
public class DriverDTO implements Serializable {
    private final Long id;
    private final String email;
    private final String name;
    private final String lastName;
    private final String phone;
    private final String profilePhoto;
    private final boolean active;
    private final boolean blocked;
    private final Long positionId;
    private final double positionLatitude;
    private final double positionLongitude;
    private final String positionName;
    private final Long vehicleId;
    private final String vehicleName;
    private final String vehiclePlateNumber;
    private final int vehicleCapacity;
    private final VehicleType vehicleType;
    private final DriverStatus driverStatus;

    public DriverDTO(Driver driver){
        this.id = driver.getId();
        this.email = driver.getEmail();
        this.name = driver.getName();
        this.lastName = driver.getLastName();
        this.phone = driver.getPhone();
        this.profilePhoto = driver.getProfilePhoto();
        this.active = driver.isActive();
        this.blocked = driver.isBlocked();
        this.positionId = driver.getPosition().getId();
        this.positionLatitude = driver.getPosition().getLatitude();
        this.positionLongitude = driver.getPosition().getLongitude();
        this.positionName = driver.getPosition().getName();
        this.vehicleId = driver.getVehicle().getId();
        this.vehicleName = driver.getVehicle().getName();
        this.vehiclePlateNumber = driver.getVehicle().getPlateNumber();
        this.vehicleCapacity = driver.getVehicle().getCapacity();
        this.vehicleType = driver.getVehicle().getType();
        this.driverStatus = driver.getDriverStatus();
    }
    public DriverDTO(Long id, String email, String name, String lastName, String phone, String profilePhoto, boolean active, boolean blocked, Long positionId, double positionLatitude, double positionLongitude, Integer positionStopNumber, String positionName, Long vehicleId, String vehicleName, String vehiclePlateNumber, int vehicleCapacity, VehicleType vehicleType, DriverStatus driverStatus) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.profilePhoto = profilePhoto;
        this.active = active;
        this.blocked = blocked;
        this.positionId = positionId;
        this.positionLatitude = positionLatitude;
        this.positionLongitude = positionLongitude;
        this.positionName = positionName;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleType = vehicleType;
        this.driverStatus = driverStatus;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public boolean getActive() {
        return active;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public Long getPositionId() {
        return positionId;
    }

    public double getPositionLatitude() {
        return positionLatitude;
    }

    public double getPositionLongitude() {
        return positionLongitude;
    }


    public String getPositionName() {
        return positionName;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDTO entity = (DriverDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.profilePhoto, entity.profilePhoto) &&
                Objects.equals(this.active, entity.active) &&
                Objects.equals(this.blocked, entity.blocked) &&
                Objects.equals(this.positionId, entity.positionId) &&
                Objects.equals(this.positionLatitude, entity.positionLatitude) &&
                Objects.equals(this.positionLongitude, entity.positionLongitude) &&
                Objects.equals(this.positionName, entity.positionName) &&
                Objects.equals(this.vehicleId, entity.vehicleId) &&
                Objects.equals(this.vehicleName, entity.vehicleName) &&
                Objects.equals(this.vehiclePlateNumber, entity.vehiclePlateNumber) &&
                Objects.equals(this.vehicleCapacity, entity.vehicleCapacity) &&
                Objects.equals(this.vehicleType, entity.vehicleType) &&
                Objects.equals(this.driverStatus, entity.driverStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, lastName, phone, profilePhoto, active, blocked, positionId, positionLatitude, positionLongitude, positionName, vehicleId, vehicleName, vehiclePlateNumber, vehicleCapacity, vehicleType, driverStatus);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "name = " + name + ", " +
                "lastName = " + lastName + ", " +
                "phone = " + phone + ", " +
                "profilePhoto = " + profilePhoto + ", " +
                "active = " + active + ", " +
                "blocked = " + blocked + ", " +
                "positionId = " + positionId + ", " +
                "positionLatitude = " + positionLatitude + ", " +
                "positionLongitude = " + positionLongitude + ", " +
                "positionName = " + positionName + ", " +
                "vehicleId = " + vehicleId + ", " +
                "vehicleName = " + vehicleName + ", " +
                "vehiclePlateNumber = " + vehiclePlateNumber + ", " +
                "vehicleCapacity = " + vehicleCapacity + ", " +
                "vehicleType = " + vehicleType + ", " +
                "driverStatus = " + driverStatus + ")";
    }
}