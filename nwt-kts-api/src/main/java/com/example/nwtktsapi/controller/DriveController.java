package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "api/drive")
public class DriveController {


    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/startDrive")
    public ResponseEntity<String> startDrive(){

        List<String> values = Arrays.asList("radi","radiliii","789");
        String url = "http://localhost:3000/receive-values";
        HttpEntity<List<String>> request = new HttpEntity<>(values);
        restTemplate.postForObject(url, request,Void.class);


        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private DriverService driverService;
    @PostMapping(value = "test")
    public ResponseEntity<String> test(){
        Driver driver = driverService.getAllDrivers().get(0);
        driver.setDriverStatus(DriverStatus.DRIVING);
        notificationService.sendDriverChangeStaus(driver);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
