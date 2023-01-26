package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.*;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired RideService rideService;


    public void sendDriverNewRideRequest(Driver driver, RideDTO rideDTO){

        NewRideRequestDriverDTO sendValues = new NewRideRequestDriverDTO();
        sendValues.setDriverId( driver.getId().toString() );
        sendValues.setRideDTO(rideDTO);
        String url = "http://localhost:3000/notify-driver";
        HttpEntity<NewRideRequestDriverDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }


    public void sendDriverAcceptedRide(Driver driver, RideDTO rideDTO){

        NewRideRequestDriverDTO sendValues = new NewRideRequestDriverDTO();
        sendValues.setDriverId( driver.getId().toString() );
        sendValues.setRideDTO(rideDTO);
        String url = "http://localhost:3000/driver-accepted";
        HttpEntity<NewRideRequestDriverDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }
    public void sendClientConfirmRide(Driver driver, RideDTO rideDTO){

        NewRideRequestDriverDTO sendValues = new NewRideRequestDriverDTO();
        sendValues.setDriverId( driver.getId().toString() );
        sendValues.setRideDTO(rideDTO);
        String url = "http://localhost:3000/client-confirmed";
        HttpEntity<NewRideRequestDriverDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }

    public void sendDriverChangeStaus(Driver driver){
        DriverChangeStatusDTO sendValues = new DriverChangeStatusDTO(driver);
        String url = "http://localhost:3000/driver-change-status";
        HttpEntity<DriverChangeStatusDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }

    public void startRideSimulation(Fare fare){
        Gson gson = new Gson();
        RideSimulationDTO sendValues = new RideSimulationDTO();
        sendValues.setDriver(fare.getDriver().getVehicle().getPlateNumber());
        List<List<Float>> listOfList = gson.fromJson(fare.getPathForRide().getCords(), new TypeToken<List<List<Float>>>() {}.getType());
        sendValues.setValues(listOfList);
        String url = "http://localhost:3000/driveSimulation";
        HttpEntity<RideSimulationDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }

    @Transactional
    public void sentReservationNotification(Long fareId , String message){
        Gson gson = new Gson();
        NotificationReservationDTO sendValues = new NotificationReservationDTO();
        Fare fare = rideService.getFareById(fareId);
        RideDTO rideDTO = new RideDTO(fare);
        sendValues.setRideDTO(rideDTO);
        sendValues.setMessage(message);
        String url = "http://localhost:3000/notify-for-reservation";
        HttpEntity<NotificationReservationDTO> request = new HttpEntity<>(sendValues);
        restTemplate.postForObject(url, request,Void.class);
    }

}
