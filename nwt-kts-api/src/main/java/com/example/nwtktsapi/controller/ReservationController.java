package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.MakeReservationDTO;
import com.example.nwtktsapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping(value = "api/reservations/")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("newReservation")
    public ResponseEntity<?> reserveRide(@RequestBody MakeReservationDTO newReservation){

        System.out.println(newReservation.toString());

        reservationService.addReservationInScheduledTasks(newReservation.getRideId(), newReservation.getTime());


        return ResponseEntity.ok().body("OK");
    }
}
