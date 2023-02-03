package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.*;
import com.example.nwtktsapi.repository.RideRepository;
import com.example.nwtktsapi.repository.UserRepository;
import com.example.nwtktsapi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private UserRepository userRepository;

    public Fare getCurrentDriverFare(Long driverId) {
        List<Fare> res =  rideRepository.getCurrentDriverFare(driverId);
        if(res.size() > 0)
            return res.get(0);
        return null;
    }

    public Fare getFareById(Long id){
        return rideRepository.findByFareID(id);
    }

    public Fare saveFare(Fare fare){
        return  rideRepository.save(fare);
    }

    public Long notifySplitFare(RideDTO rideDTO) throws ExecutionException, InterruptedException {
        SplitFare splitFare = splitFareService.crateNewSplitFare();
        //CompletableFuture[] futures = new CompletableFuture[rideDTO.getSplitFare().length];
        float price = rideDTO.getPrice()/(rideDTO.getSplitFare().length+1);
        for (int i = 0; i < rideDTO.getSplitFare().length; i++) {
            String email = rideDTO.getSplitFare()[i];
            //futures[i] = CompletableFuture.runAsync(() -> {
            emailService.sendSplitFareAgreement(email, rideDTO.getLocationsNames(), price, splitFare.getId());
            //});
        }
        //CompletableFuture.allOf(futures).get();
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

    public Fare save(RideDTO rideDTO, Driver driver) {
        System.out.println(rideDTO.toString());

        Fare fare = new Fare();
        List<Client> clients = new ArrayList<>();
        clients.add((Client) userRepository.findById(rideDTO.getClientId()).get());
        for(String clientEmail: rideDTO.getSplitFare()){
            Client client = (Client) userRepository.findByEmail(clientEmail);
            clients.add(client);
        }
        fare.setClients(clients);
        fare.setDriver(driver);
        fare.setStops(rideDTO.getLocations());

        fare.setStartAddress(rideDTO.getLocationsNames().get(0));
        fare.setStartAddress(rideDTO.getLocationsNames().get(rideDTO.getLocationsNames().size() - 1));
        fare.setPrice(rideDTO.getPrice());
        fare.setRequestTime(LocalDateTime.now());
        fare.setStartTime(LocalDateTime.now().plusMinutes(5)); //
        fare.setEndTime(LocalDateTime.now().plusMinutes(rideDTO.getDuration() + 5));
        fare.setAccepted(true); // ili false
        fare.setReservation(rideDTO.isReservation());
        fare.setDistance(rideDTO.getDistance());
        fare.setActive(!rideDTO.isReservation());
        fare.setRouteIndex(rideDTO.getRouteIndex());

        PathForRide newPath = new PathForRide();
        newPath.setCords(rideDTO.getPathForRide());
        fare.setPathForRide(newPath);

        return rideRepository.save(fare);

    }

    public boolean isClinetInRide(Long id){
        User client = userRepository.clientInRide(id);
        return client != null;
    }
}
