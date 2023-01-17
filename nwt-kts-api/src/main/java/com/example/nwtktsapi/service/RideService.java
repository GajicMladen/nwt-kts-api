package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.SplitFare;
import com.example.nwtktsapi.repository.RideRepository;
import com.example.nwtktsapi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SplitFareService splitFareService;

    public Fare getCurrentDriverFare(Long driverId) {
        return rideRepository.getCurrentDriverFare(driverId);
    }

    public Long notifySplitFare(RideDTO rideDTO) throws ExecutionException, InterruptedException {
        SplitFare splitFare = splitFareService.crateNewSplitFare();
        CompletableFuture[] futures = new CompletableFuture[rideDTO.getSplitFare().length];
        float price = rideDTO.getPrice()/(rideDTO.getSplitFare().length+1);
        for (int i = 0; i < rideDTO.getSplitFare().length; i++) {
            String email = rideDTO.getSplitFare()[i];
            futures[i] = CompletableFuture.runAsync(() -> {
                emailService.sendSplitFareAgreement(email, rideDTO.getLocationsNames(), price, splitFare.getId());
            });
        }
        CompletableFuture.allOf(futures).get();
        return splitFare.getId();
    }

    public int waitForSplitFareAgreement(int numberToSplit, Long id) throws InterruptedException {
        int currentSecond = 0;
        while (currentSecond < 120) {
            int count = splitFareService.getValueById(id);
            if (count == numberToSplit) {
                break;
            }
            Thread.sleep(1000);
            currentSecond++;
        }
        return currentSecond;
    }

    public Long save(RideDTO rideDTO, Driver driver) {
        Fare fare = new Fare();
        //setClients
        fare.setDriver(driver);
        fare.setStops(rideDTO.getLocations());
        //setPayments
        fare.setPrice(rideDTO.getPrice());
        fare.setRequestTime(LocalDateTime.now());
        fare.setStartTime(LocalDateTime.now().plusMinutes(5)); //
        fare.setEndTime(LocalDateTime.now().plusMinutes(rideDTO.getDuration() + 5));
        fare.setAccepted(true); // ili false
        fare.setReservation(false);
        fare.setDistance(rideDTO.getDistance());
        fare.setActive(true);

        return rideRepository.save(fare).getFareID();

    }
}
