package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.NotificationService;
import com.example.nwtktsapi.service.TransformToDTOService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "api/drivers/")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private TransformToDTOService transformToDTOService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Object>> getAllDrivers(){
        List<Driver> drivers = driverService.getAllDrivers();
        List<Object> driversDTO = transformToDTOService.transformToDTOList(drivers);
        return new ResponseEntity<>(driversDTO, HttpStatus.OK);
    }

    @GetMapping(value = "driverStatus/{id}")
    public ResponseEntity<DriverStatus> getDriverStatus(@PathVariable Long id){
        Driver driver = driverService.getDriverById(id);
        if (driver != null)
            return ResponseEntity.ok().body(driver.getDriverStatus());
        return ResponseEntity.status(404).body(null);
    }

    @PostMapping(value = "changeDriverStatus/{id}")
    public ResponseEntity<?> changeDriverStatus(@RequestBody boolean available, @PathVariable Long id){
        Gson gson = new Gson();
        Driver driver = driverService.getDriverById(id);
        if (driver == null)
            return ResponseEntity.status(404).body(null);
        driverService.changeDriverStatus(driver,available);
        notificationService.sendDriverChangeStaus(driver);
        return ResponseEntity.ok().body(gson.toJson("OK"));
    }
}
