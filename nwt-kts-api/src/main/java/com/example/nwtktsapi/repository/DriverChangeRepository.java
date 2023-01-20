package com.example.nwtktsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.DriverChangeRequest;

public interface DriverChangeRepository extends JpaRepository<DriverChangeRequest,Long> {

}
