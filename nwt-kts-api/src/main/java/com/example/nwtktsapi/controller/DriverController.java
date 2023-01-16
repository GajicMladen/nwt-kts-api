package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.TransformToDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "api/drivers/")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private TransformToDTOService transformToDTOService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Object>> getAllDrivers(){
        List<Driver> drivers = driverService.getAllDrivers();
        List<Object> driversDTO = transformToDTOService.transformToDTOList(drivers);
        return new ResponseEntity<>(driversDTO, HttpStatus.OK);
    }
}
