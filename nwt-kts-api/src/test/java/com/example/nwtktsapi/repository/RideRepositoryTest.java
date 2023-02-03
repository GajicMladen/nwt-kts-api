package com.example.nwtktsapi.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.nwtktsapi.model.Fare;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    void testFindByFareID() {
    	Fare fare = new Fare();
    	fare.setReservation(true);
        fare.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare = rideRepository.save(fare);
        
        assertEquals(fare, rideRepository.findByFareID(fare.getId()));
    }
    
    @Test
    @Transactional
    void testFindByIsReservationTrue() {
        Fare fare = new Fare();
        fare.setReservation(true);
        fare.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.findByIsReservation(true);
        assertEquals(1, fares.size());
        assertTrue(fares.get(0).isReservation());
    }
    
    @Test
    @Transactional
    void testFindByIsReservationFalse() {
        Fare fare = new Fare();
        fare.setReservation(false);
        fare.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare = rideRepository.save(fare);

        List<Fare> fares = rideRepository.findByIsReservation(true);
        assertFalse(fares.contains(fare));
    }

    @Test
    @Transactional
    void testFindByStartTimeBetween() {
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 5));
        rideRepository.save(fare1);

        Fare fare2 = new Fare();
        fare2.setReservation(false);
        fare2.setStartTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare2.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 7));
        rideRepository.save(fare2);

        List<Fare> fares = rideRepository.findByStartTimeBetween(
                LocalDateTime.of(2022, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 3, 0, 0)
        );
        assertEquals(2, fares.size());
        assertTrue(fares.get(0).isReservation());
    }
    
    @Test
    @Transactional
    void testFindByStartTimeBetween_None() {
    	List<Fare> fares = rideRepository.findByStartTimeBetween(LocalDateTime.of(2025,  1, 1,0,0), LocalDateTime.of(2026, 1, 1, 0,0));
    	assertEquals(fares.size(), 0);
    }

    @Test
    @Transactional
    void testGetCurrentDriverFare() {
        Driver driver = Constants.testDriver;
        driverRepository.save(driver);
        Fare fare = new Fare();
        fare.setActive(true);
        fare.setDriver(driver);
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.getCurrentDriverFare(driver.getId());
        assertEquals(1, fares.size());
        assertTrue(fares.get(0).isActive());
        assertEquals(driver.getId(), fares.get(0).getDriver().getId());
    }

    @Test
    @Transactional
    void testGetCurrentDriverFare_notFound() {
        Driver driver = Constants.testDriver;
        driverRepository.save(driver);
        Fare fare = new Fare();
        fare.setActive(false);
        fare.setDriver(driver);
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.getCurrentDriverFare(driver.getId());
        List<Fare> expected = new ArrayList<>();
        assertEquals(expected, fares);
    }

    @Test
    @Transactional
    void testFindByDriver_IdAndStartTimeBetween() {
        Driver driver = Constants.testDriver;
        driverRepository.save(driver);
        Fare fare = new Fare();
        fare.setDriver(driver);
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 2, 0, 0);
        fare.setStartTime(startTime);
        fare.setEndTime(endTime);
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.findByDriver_IdAndStartTimeBetween(driver.getId(), startTime, endTime);
        assertEquals(1, fares.size());
        assertEquals(driver.getId(), fares.get(0).getDriver().getId());
        assertEquals(startTime, fares.get(0).getStartTime());
        assertEquals(endTime, fares.get(0).getEndTime());
    }
    
    @Test
    @Transactional
    void testFindByDriver_IdAndStartTimeBetween_None() {
        Driver driver = Constants.testDriver;
        driver = driverRepository.save(driver);
        Fare fare = new Fare();
        fare.setDriver(Constants.testDriver2);
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 2, 0, 0);
        fare.setStartTime(startTime);
        fare.setEndTime(endTime);
        fare = rideRepository.save(fare);

        List<Fare> fares = rideRepository.findByDriver_IdAndStartTimeBetween(driver.getId(), startTime, endTime);
        assertEquals(fares.size(), 0);
    }

    @Test
    @Transactional
    void testFindFaresByClientIdAndStartTimeBetween() {
        Client client = Constants.testClient;
        userRepository.save(client);
        Fare fare = new Fare();
        fare.getClients().add(client);
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 2, 0, 0);
        fare.setStartTime(startTime);
        fare.setEndTime(endTime);
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.findFaresByClientIdAndStartTimeBetween(client.getId(), startTime, endTime);
        assertEquals(1, fares.size());
        assertEquals(1, fares.get(0).getClients().size());
        assertEquals(client.getId(), fares.get(0).getClients().get(0).getId());
        assertEquals(startTime, fares.get(0).getStartTime());
        assertEquals(endTime, fares.get(0).getEndTime());
    }
    
    @Test
    @Transactional
    void testFindFaresByClientIdAndStartTimeBetween_None() {
        Client client = Constants.testClient;
        client = userRepository.save(client);
        Client client2 = Constants.testClient2;
        client2 = userRepository.save(client2);
        Fare fare = new Fare();
        fare.getClients().add(client2);
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 2, 0, 0);
        fare.setStartTime(startTime);
        fare.setEndTime(endTime);
        rideRepository.save(fare);

        List<Fare> fares = rideRepository.findFaresByClientIdAndStartTimeBetween(client.getId(), startTime, endTime);
        assertEquals(0, fares.size());
    }
    
    
    @Test
    @Transactional
    void testGetReservationsInPeriod() {
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 10));
        fare1.setDone(false);
        rideRepository.save(fare1);

        Fare fare2 = new Fare();
        fare2.setReservation(true);
        fare2.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare2.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 7));
        fare2.setDone(false);
        rideRepository.save(fare2);

        Fare fare3 = new Fare();
        fare3.setReservation(false);
        fare3.setStartTime(LocalDateTime.of(2022, 3, 1, 0, 0));
        fare3.setEndTime(LocalDateTime.of(2022, 3, 1, 0, 10));
        fare3.setDone(false);
        rideRepository.save(fare3);

        List<Fare> fares = rideRepository.getReservationsInPeriod(true, LocalDateTime.of(2021, 12, 31, 0, 0), LocalDateTime.of(2022, 2, 3, 0, 0));
        assertEquals(2, fares.size());
        assertTrue(fares.get(0).isReservation());
        assertFalse(fares.get(0).isDone());
        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0), fares.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 7), fares.get(1).getEndTime());
    }

    @Test
    @Transactional
    void testGetReservationsInPeriod_ValidData_ShouldReturnReservations() {
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 10));
        fare1.setDone(false);
        rideRepository.save(fare1);

        Fare fare2 = new Fare();
        fare2.setReservation(true);
        fare2.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare2.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 7));
        fare2.setDone(false);
        rideRepository.save(fare2);

        List<Fare> fares = rideRepository.getReservationsInPeriod(true, LocalDateTime.of(2021, 12, 31, 0, 0), LocalDateTime.of(2022, 1, 5, 0, 0));
        assertEquals(2, fares.size());
        assertTrue(fares.get(0).isReservation());
        assertTrue(fares.get(1).isReservation());
    }

    @Test
    @Transactional
    void testGetReservationsInPeriod_ReservationDone_ShouldReturnEmptyList() {
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare1.setDone(true);
        rideRepository.save(fare1);

        Fare fare2 = new Fare();
        fare2.setReservation(true);
        fare2.setStartTime(LocalDateTime.of(2022, 1, 3, 0, 0));
        fare2.setEndTime(LocalDateTime.of(2022, 1, 4, 0, 0));
        fare2.setDone(false);
        rideRepository.save(fare2);

        List<Fare> fares = rideRepository.getReservationsInPeriod(true, LocalDateTime.of(2021, 12, 31, 0, 0), LocalDateTime.of(2022, 1, 5, 0, 0));
        assertEquals(1, fares.size());
        assertTrue(fares.get(0).isReservation());
    }

    @Test
    @Transactional
    void testGetReservationsInPeriod_OneFalse() {
        // Arrange
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2022, 1, 2, 0, 0));
        fare1.setDone(false);
        rideRepository.save(fare1);

        Fare fare2 = new Fare();
        fare2.setReservation(true);
        fare2.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare2.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare2.setDone(false);
        rideRepository.save(fare2);

        Fare fare3 = new Fare();
        fare3.setReservation(false);
        fare3.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare3.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare3.setDone(false);
        rideRepository.save(fare3);

        Fare fare4 = new Fare();
        fare4.setReservation(true);
        fare4.setStartTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare4.setEndTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        fare4.setDone(true);
        rideRepository.save(fare4);

        // Act
        List<Fare> fares = rideRepository.getReservationsInPeriod(true, LocalDateTime.of(2021, 12, 31, 0, 0), LocalDateTime.of(2022, 1, 5, 0, 0));

        // Assert
        assertEquals(2, fares.size());
        assertTrue(fares.get(0).isReservation());
        assertTrue(fares.get(1).isReservation());
    }
    
    @Test
    @Transactional
    void testFindByReservationAndStartTimeGreater() {
        Fare fare1 = new Fare();
        fare1.setReservation(true);
        fare1.setStartTime(LocalDateTime.of(2023, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2023, 1, 2, 0, 0));
        fare1.setDone(false);
        fare1 = rideRepository.save(fare1);
        
        List<Fare> fares = rideRepository.findByIsReservationAndStartTimeGreaterThan(true, LocalDateTime.of(2022, 12, 12, 0, 0));
        assertTrue(fares.contains(fare1));
    }
    
    @Test
    @Transactional
    void testFindByReservationAndStartTimeGreaterNonRes() {
        Fare fare1 = new Fare();
        fare1.setReservation(false);
        fare1.setStartTime(LocalDateTime.of(2023, 1, 1, 0, 0));
        fare1.setEndTime(LocalDateTime.of(2023, 1, 2, 0, 0));
        fare1.setDone(false);
        fare1 = rideRepository.save(fare1);
        
        List<Fare> fares = rideRepository.findByIsReservationAndStartTimeGreaterThan(false, LocalDateTime.of(2022, 12, 12, 0, 0));
        assertTrue(fares.contains(fare1));
    }
}
