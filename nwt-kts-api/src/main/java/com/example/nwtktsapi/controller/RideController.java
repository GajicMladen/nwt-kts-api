package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.NewRideRequestDriverDTO;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.*;
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
import java.util.ArrayList;
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

    @Autowired ReservationService reservationService;

    private String MAP_REDIRECT = "http://localhost:4200/clientmap";

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
    @PostMapping(value = "order")
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> orderRide(@RequestBody RideDTO rideDTO, Principal principal) throws ExecutionException, InterruptedException {
        Gson gson = new Gson();
        System.out.println(rideDTO.toString());

        Long splitFareId = rideService.notifySplitFare(rideDTO);
        int secondsPassed = rideService.waitForSplitFareAgreement(rideDTO.getSplitFare().length, splitFareId);
        if (secondsPassed == 120) {
            return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(gson.toJson(new ErrMsg("Naplata nije odobrena!")));
        }

        User client = userService.findByEmail(principal.getName());
        if(client.getTokens() < rideDTO.getPrice()) {
            if (rideDTO.getSplitFare().length > 0 && client.getTokens() < rideDTO.getPrice() / rideDTO.getSplitFare().length) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(gson.toJson(new ErrMsg("Nemate dovoljno tokena,iako se vožnja deli...")));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(gson.toJson(new ErrMsg("Nemate dovoljno tokena")));
            }
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
        // System.out.println(driver.getName() + ' ' + driver.getLastName());
        rideDTO.setDriverId(driver.getId());
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

        return ResponseEntity.ok().body(gson.toJson(driver.getVehicle().getPlateNumber()));
    }

    //Add principal (moved from here because of tests!)
    @PostMapping(value = "clientConfirmRide")
    public ResponseEntity<?> clientConfirmRide(@RequestBody RideDTO rideDTO){
        Gson gson = new Gson();
        List<User> usersToPay = new ArrayList<>();
        Driver driver = driverService.getDriverById(rideDTO.getDriverId());
        User client = userService.getUserById(rideDTO.getClientId());
        for(String passengersMail : rideDTO.getSplitFare()){
            User passenger = userService.findByEmail(passengersMail);
            usersToPay.add(passenger);
        }
        usersToPay.add(client);
        Fare fare = rideService.save(rideDTO, driver);

        tokensService.removeTokensFromUsers(usersToPay,rideDTO.getPrice());
        tokensService.addTokensForUser(driver,rideDTO.getPrice());

        notificationService.sendClientConfirmRide(driver, rideDTO);

        if(rideDTO.isReservation()){
            reservationService.addReservationInScheduledTasks(fare.getId(), rideDTO.getStartTime());
            return ResponseEntity.ok().body(gson.toJson("Voznja je rezervisana!"));
        }else {
            driver.setDriverStatus(DriverStatus.DRIVING);
            driverService.save(driver);
            notificationService.sendDriverChangeStaus(driver);
            notificationService.startRideSimulation(fare);
            return ResponseEntity.ok().body(gson.toJson("Voznja je pocela"));
        }

    }
    @PostMapping(value = "finishRide")
    public ResponseEntity<?> finishRide(@RequestBody RideDTO rideDTO, Principal principal){
        Gson gson = new Gson();
        Fare fare = rideService.getFareById(rideDTO.getRideId());
        fare.setActive(false);
        fare.setDone(true);
        rideService.saveFare(fare);

        Driver driver = driverService.getDriverById(rideDTO.getDriverId());
        driverService.changeDriverStatus(driver,true);
        driverService.save(driver);

        notificationService.sendDriverChangeStaus(driver);

        return ResponseEntity.ok().body(gson.toJson("Voznja je zavrsena!"));
    }


    @GetMapping(value = "activeRideForDriver/{id}")
    public  ResponseEntity<?> activeRideForDriver(@PathVariable Long id){
        Gson gson = new Gson();
        Fare currentRide =  rideService.getCurrentDriverFare(id);
        if(currentRide != null){
            return ResponseEntity.ok().body(new RideDTO(currentRide));
        }
        return  ResponseEntity.status(404).body("Vozac nema trenutnu voznju");
    }
}
