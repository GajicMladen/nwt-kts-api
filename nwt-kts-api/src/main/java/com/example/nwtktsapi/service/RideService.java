package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.SplitFare;
import com.example.nwtktsapi.repository.RideRepository;
import com.example.nwtktsapi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
