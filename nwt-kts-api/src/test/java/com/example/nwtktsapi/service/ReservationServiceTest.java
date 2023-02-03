package com.example.nwtktsapi.service;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.repository.DriverRepository;
import com.example.nwtktsapi.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationService reservationService ;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private NotificationService notificationService;


    @BeforeEach
    public void setUp(){
        reservationService = new ReservationService();
        MockitoAnnotations.openMocks(this);
        List<Fare> fares = new ArrayList<>();
        fares.add(Constants.testFare1);
        fares.add(Constants.testFare2);
        fares.add(Constants.testFare3);

        when(rideRepository.findByIsReservation(true)).thenReturn(fares);
        when(rideRepository.findByIsReservationAndStartTimeGreaterThan(true, Constants.VALID_START_TIME))
                .thenReturn(fares);
        when(rideRepository.findByFareID(Constants.VALID_FARE_ID)).thenReturn(Constants.testFare1);
        reservationService.setRideRepository(rideRepository);
        reservationService.setDriverRepository(driverRepository);
        reservationService.setNotificationService(notificationService);
    }

    @Test
    public void getAllReservations_shouldBe3(){
        List<Fare> fares = reservationService.getAllReservations();
        assertEquals(3, fares.size());
    }

    @Test
    public void getAllFutureReservation_shouldBe3(){
        List<Fare> fares = reservationService.getAllFutureReservation(Constants.VALID_START_TIME);
        assertEquals(3,fares.size());
    }
    
    @Test
    public void getAllFutureReservations_shouldBe0() {
    	List<Fare> fares = reservationService.getAllFutureReservation(LocalDateTime.of(2023,1,1,0,0));
    	assertEquals(0, fares.size());
    }

    @Test
    public void startFare_shouldBeOK(){
        Constants.testFare1.setDriver(Constants.testDriver);
        reservationService.startFare(Constants.VALID_FARE_ID);
        assertEquals(DriverStatus.DRIVING,Constants.testDriver.getDriverStatus());
        verify(driverRepository,times(1)).save(Constants.testDriver);
        verify(rideRepository,times(1)).save(Constants.testFare1);
        verify(notificationService,times(1)).sendDriverChangeStaus(Constants.testDriver);
        verify(notificationService,times(1)).startRideSimulation(Constants.testFare1);
    }
    
    @Test
    public void startFare_shouldFail() {
    	Constants.testDriver.setDriverStatus(DriverStatus.AVAILABLE);
    	Constants.testFare1.setDriver(Constants.testDriver);
    	reservationService.startFare(Constants.INVALID_FARE_ID_2);
        assertEquals(DriverStatus.AVAILABLE ,Constants.testDriver.getDriverStatus());
        verify(driverRepository,times(0)).save(Constants.testDriver);
        verify(rideRepository,times(0)).save(Constants.testFare1);
        verify(notificationService,times(0)).sendDriverChangeStaus(Constants.testDriver);
        verify(notificationService,times(0)).startRideSimulation(Constants.testFare1);
    }

}
