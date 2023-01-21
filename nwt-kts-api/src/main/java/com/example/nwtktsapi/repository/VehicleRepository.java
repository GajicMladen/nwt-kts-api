package com.example.nwtktsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
