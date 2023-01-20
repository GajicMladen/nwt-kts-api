package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.DriverChangeStatusDTO;
import com.example.nwtktsapi.dto.NewRideRequestDriverDTO;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;


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

}
