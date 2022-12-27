package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.model.VehicleType;
import com.example.nwtktsapi.service.VehiclePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "api/vehicleprice/")
public class VehiclePriceController {

    @Autowired
    private VehiclePriceService vehiclePriceService;

    @GetMapping(value = "price")
    public ResponseEntity<?> getPrice(@RequestParam int typeNumber) {
        System.out.println(typeNumber);
        VehicleType type = VehicleType.values()[typeNumber];
        int price = vehiclePriceService.getTypePrice(type);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(price);
    }
}
