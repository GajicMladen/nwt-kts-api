package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.NewRideRequestDriverDTO;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.SplitFare;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.*;
import com.example.nwtktsapi.utils.EmailService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.jws.soap.SOAPBinding;
import java.net.URI;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping(value = "api/ride/")
public class RideController {

    @Autowired
    private UserService userService;
    @Autowired
    private RideService rideService;

    @Autowired
    private SplitFareService splitFareService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private  TokensService tokensService;


    private String MAP_REDIRECT = "http://localhost:4200/clientmap";

    @PostMapping(value = "order")
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> orderRide(@RequestBody RideDTO rideDTO, Principal principal) throws ExecutionException, InterruptedException {
        Gson gson = new Gson();

        Long splitFareId = rideService.notifySplitFare(rideDTO);
        int secondsPassed = rideService.waitForSplitFareAgreement(rideDTO.getSplitFare().length, splitFareId);
        if (secondsPassed == 120) {
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(gson.toJson(new ErrMsg("Naplata nije odobrena!")));
        }

        User client = userService.findByEmail(principal.getName());
        if(client.getTokens() < rideDTO.getPrice()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(gson.toJson(new ErrMsg("Nemate dovoljno tokena")));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(gson.toJson("Vožnja je poručena!"));
    }

    @PostMapping(value = "getDriverForRide")
    public ResponseEntity<?> getDriverForRide(@RequestBody RideDTO rideDTO, Principal principal){
        Gson gson = new Gson();
        Driver driver = driverService.getSuitedDriver(rideDTO);
        if (driver == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(gson.toJson(new ErrMsg("Nažalost trenutno nema slobodnih vozača!")));
        }
        System.out.println(driver.getName() + ' ' + driver.getLastName());

        //Send driver a notification
        notificationService.sendDriverNewRideRequest(driver,rideDTO);

        return ResponseEntity.ok().body(gson.toJson(rideDTO));
    }

    @PostMapping(value = "acceptRide")
    public ResponseEntity<?> acceptRide(@RequestBody RideDTO rideDTO, Principal principal){
        Gson gson = new Gson();
        Driver driver = driverService.getDriverById(rideDTO.getDriverId());
        rideDTO.setDriverId(driver.getId());
        rideDTO.setVehiclePlateNumber( driver.getVehicle().getPlateNumber() );

        notificationService.sendDriverAcceptedRide(driver,rideDTO);
//        System.out.println("Prihvacena voznja");
        rideService.save(rideDTO, driver);

        User user = userService.getUserById(rideDTO.getClientId());
        tokensService.removeTokensFromUser(user,rideDTO.getPrice());

        return ResponseEntity.ok().body(gson.toJson(driver.getVehicle().getPlateNumber()));
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
