package com.example.nwtktsapi.service;

import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.repository.DriverRepository;
import com.example.nwtktsapi.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {
    private final Map<Long, LocalDateTime> scheduledTasks = new HashMap<>();

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private NotificationService notificationService;

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public List<Fare> getAllReservations(){
        return  rideRepository.findByIsReservation(true);
    }

    public List<Fare> getAllFutureReservation(LocalDateTime startDateTime){
        return rideRepository.findByIsReservationAndStartTimeGreaterThan(true,startDateTime);
    }

    @Scheduled( fixedRate = 60000)
    public void checkReservations(){
        try {
            System.out.println("checkig reservations");
            Iterator<Map.Entry<Long, LocalDateTime>> iterator = scheduledTasks.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, LocalDateTime> entry = iterator.next();
                Long fareId = entry.getKey();
                LocalDateTime reservationTime = entry.getValue();
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(reservationTime) ) {
                    System.out.println("pokretanje voznje"+fareId);
                    startFare(fareId);
                    iterator.remove();
                }else{
                    long minutes = ChronoUnit.MINUTES.between(reservationTime,now);
                    if(minutes == 15){
                        notificationService.sentReservationNotification(fareId,
                                "Podsetnik! 15 minuta do pčetka rezervisane vožnje.");
                    }
                    else if(minutes < 15){
                        if(minutes % 5 == 0)
                            notificationService.sentReservationNotification(fareId,
                                    "Podsetnik! "+Math.abs(minutes)+" minuta do pčetka rezervisane vožnje.");
                    }
                    notificationService.sentReservationNotification(fareId,
                            "Podsetnik! Vožnja za "+Math.abs(minutes)+" minuta.");
                }
            }
            System.out.println(scheduledTasks.size());
            System.out.println("================");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(scheduledTasks.size());
        }
    }

    public void startFare(Long fareId){
        Fare fare = rideRepository.findByFareID(fareId);
        if( fare == null)
            return;
        fare.setActive(true);
        Driver driver = fare.getDriver();
        driver.setDriverStatus(DriverStatus.DRIVING);
        driverRepository.save(driver);
        rideRepository.save(fare);
        notificationService.sendDriverChangeStaus(driver);
        notificationService.startRideSimulation(fare);
    }
    public void addReservationInScheduledTasks(Long rideId, LocalDateTime localDateTime){
        scheduledTasks.put(rideId,localDateTime);
    }
}
