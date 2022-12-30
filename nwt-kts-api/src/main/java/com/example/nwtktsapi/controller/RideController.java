package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.SplitFare;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.RideService;
import com.example.nwtktsapi.service.SplitFareService;
import com.example.nwtktsapi.utils.EmailService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping(value = "api/ride/")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private SplitFareService splitFareService;

    @Autowired
    private DriverService driverService;

    private String MAP_REDIRECT = "http://localhost:4200/clientmap";

    @PostMapping(value = "order")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> orderRide(@RequestBody RideDTO rideDTO, Principal principal) throws ExecutionException, InterruptedException {
        Gson gson = new Gson();
        Long splitFareId = rideService.notifySplitFare(rideDTO);
        int secondsPassed = rideService.waitForSplitFareAgreement(rideDTO.getSplitFare().length, splitFareId);
        if (secondsPassed == 120) {
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(gson.toJson(new ErrMsg("Naplata nije odobrena!")));
        }
        //TODO: Trazenje pogodnog vozaca: da li ima slobodnih, rezervacije, tip vozila
        Driver driver = driverService.getSuitedDriver(rideDTO);
        if (driver == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(gson.toJson(new ErrMsg("Nažalost trenutno nema slobodnih vozača!")));
        }
        System.out.println(driver.getName() + ' ' + driver.getLastName());
        //TODO: Naplata voznje
        //TODO: Obavesti vozaca
        //TODO: Obavesti korisnike za koliko vremena ce vozac biti tu
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson("Vožnja je poručena!"));
    }

    @GetMapping(value = "agree/{id}")
    public ResponseEntity<?> agreeToSplitFare(@PathVariable Long id ) {
        SplitFare splitFare = splitFareService.findById(id).orElse(null);
        if (splitFare == null) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        } else {
            splitFare.setNumberOfAgreed(splitFare.getNumberOfAgreed()+1);
            splitFareService.save(splitFare);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(MAP_REDIRECT)).build();
    }
}
