package com.example.nwtktsapi.service;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.*;
import com.example.nwtktsapi.repository.DriverRepository;
import com.example.nwtktsapi.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


public class DriverServiceTest {

    private DriverService driverService;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private RideService rideService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(Constants.testDriver);
        drivers.add(Constants.testDriver2);
        drivers.add(Constants.testDriver3);
        drivers.add(Constants.testDriver4);
        when(driverRepository.findAll()).thenReturn(drivers);
        when(driverRepository.findById(Constants.VALID_DRIVER_ID)).thenReturn(Optional.ofNullable(Constants.testDriver));
        when(driverRepository.findById(Constants.INVALID_DRIVER_ID)).thenReturn(Optional.empty());
        when(driverRepository.findByEmail(Constants.VALID_EMAIL)).thenReturn(Optional.ofNullable(Constants.testDriver));
        when(driverRepository.findByEmail(Constants.INVALID_EMAIL)).thenReturn(Optional.empty());
        when(driverRepository.getAvailableDrivers(VehicleType.BASIC)).thenReturn(drivers);
        when(driverRepository.getAvailableDrivers(VehicleType.LUX)).thenReturn(new ArrayList<>());
        when(driverRepository.getAvailableDrivers(VehicleType.BABY_SEAT)).thenReturn(drivers.subList(0,1));
        when(driverRepository.getAvailableDrivers(VehicleType.BIG)).thenReturn(new ArrayList<>());
        when(driverRepository.getAvailableDrivers(VehicleType.PET_FRIENDLY)).thenReturn(drivers.subList(1, 3));
        
        when(driverRepository.getDrivingDrivers(VehicleType.BASIC)).thenReturn(drivers);
        when(driverRepository.getDrivingDrivers(VehicleType.LUX)).thenReturn(new ArrayList<>());
        when(driverRepository.getDrivingDrivers(VehicleType.BABY_SEAT)).thenReturn(drivers.subList(0,1));
        when(driverRepository.getDrivingDrivers(VehicleType.BIG)).thenReturn(new ArrayList<>());
        when(driverRepository.getDrivingDrivers(VehicleType.PET_FRIENDLY)).thenReturn(drivers.subList(2, 3));
        
        
        driverService = new DriverService();
        driverService.setDriverRepository(driverRepository);

        List<Fare> fares = new ArrayList<>();
        fares.add(Constants.testFare1);
        fares.add(Constants.testFare2);
        fares.add(Constants.testFare3);
        when(rideRepository.getReservationsInPeriod(true,Constants.VALID_START_TIME,Constants.VALID_END_TIME))
                .thenReturn(fares);
        when(rideRepository.getReservationsInPeriod(true,Constants.INVALID_START_TIME,Constants.INVALID_END_TIME))
                .thenReturn(new ArrayList<>());
        when(rideRepository.getReservationsInPeriod(true,Constants.VALID_START_TIME,Constants.INVALID_END_TIME))
                .thenReturn(fares.subList(0,2));
        driverService.setRideRepository(rideRepository);

        when(rideService.getCurrentDriverFare(Constants.testDriver.getId()))
                .thenReturn(Constants.testFare1);
        when(rideService.getCurrentDriverFare(Constants.testDriver2.getId()))
                .thenReturn(Constants.testFare2);
        when(rideService.getCurrentDriverFare(Constants.testDriver3.getId()))
                .thenReturn(Constants.testFare3);
        driverService.setRideService(rideService);
    }


    @Test
    public void getAllDrivers_returnFourDrivers(){
        List<Driver> drivers = driverService.getAllDrivers();
        assertEquals(4, drivers.size());
        assertEquals(Constants.testDriver,drivers.get(0));
        assertEquals(Constants.testDriver2,drivers.get(1));
        assertEquals(Constants.testDriver3,drivers.get(2));
        assertEquals(Constants.testDriver4,drivers.get(3));
    }

    @Test
    public void getDriverById_validId(){
        Driver driver = driverService.getDriverById(Constants.VALID_DRIVER_ID);
        assertEquals(driver, Constants.testDriver);
        assertEquals(driver.getId(),Constants.testDriver.getId());
    }


    @Test
    public void getDriverById_invalidId(){
        Driver driver = driverService.getDriverById(Constants.INVALID_DRIVER_ID);
        assertNull(driver);
    }

    @Test
    public void getDriverByEmail_validId(){
        Driver driver = driverService.getDriverByEmail(Constants.VALID_EMAIL);
        assertEquals(driver, Constants.testDriver);
        assertEquals(driver.getId(),Constants.testDriver.getId());
    }


    @Test
    public void getDriverByEmail_invalidId(){
        Driver driver = driverService.getDriverByEmail(Constants.INVALID_EMAIL);
        assertNull(driver);
    }

    @Test
    public void getAvailableDrivers_forBasicType_shouldBe3(){
        List<Driver> drivers = driverService.getAvailableDrivers(VehicleType.BASIC.ordinal());
        assertEquals(4, drivers.size());
    }

    @Test
    public void getAvailableDrivers_forLuxType_shouldBe0(){
        List<Driver> drivers = driverService.getAvailableDrivers(VehicleType.LUX.ordinal());
        assertEquals(0, drivers.size());
    }

    @Test
    public void getAvailableDrivers_forBabySeatType_shouldBe2(){
        List<Driver> drivers = driverService.getAvailableDrivers(VehicleType.BABY_SEAT.ordinal());
        assertEquals(1, drivers.size());
    }
    
    @Test
    public void getAvailableDrivers_forPetFriendlyType_shouldBe2() {
        List<Driver> drivers = driverService.getAvailableDrivers(VehicleType.PET_FRIENDLY.ordinal());
        assertEquals(2, drivers.size());
    }

    @Test
    public void getAvailableDrivers_forBigType_shouldBe0() {
        List<Driver> drivers = driverService.getAvailableDrivers(VehicleType.BIG.ordinal());
        assertEquals(0, drivers.size());
    }

    @Test
    public void getDrivingDrivers_forBasicType_shouldBe3(){
        List<Driver> drivers = driverService.getDrivingDrivers(VehicleType.BASIC.ordinal());
        assertEquals(4, drivers.size());
    }

    @Test
    public void getDrivingDrivers_forLuxType_shouldBe0(){
        List<Driver> drivers = driverService.getDrivingDrivers(VehicleType.LUX.ordinal());
        assertEquals(0, drivers.size());
    }

    @Test
    public void getDrivingDrivers_forBabySeatType_shouldBe2(){
        List<Driver> drivers = driverService.getDrivingDrivers(VehicleType.BABY_SEAT.ordinal());
        assertEquals(1, drivers.size());
    }
    
    @Test
    public void getDrivingDrivers_forPetFriendlyType_shouldBe1(){
        List<Driver> drivers = driverService.getDrivingDrivers(VehicleType.PET_FRIENDLY.ordinal());
        assertEquals(1, drivers.size());
    }

    @Test
    public void getDrivingDrivers_forBigType_shouldBe0(){
        List<Driver> drivers = driverService.getDrivingDrivers(VehicleType.BIG.ordinal());
        assertEquals(0, drivers.size());
    }

    @Test
    public void getDriversThathHaveReservationInPeriod_shouldBe3(){
        List<Driver> drivers = driverService.getDriversThatHaveReservationInPeriod(Constants.VALID_START_TIME,Constants.VALID_END_TIME);
        assertEquals(3, drivers.size());
    }

    @Test
    public void getDriversThathHaveReservationInPeriod_shouldBe0(){
        List<Driver> drivers = driverService.getDriversThatHaveReservationInPeriod(Constants.INVALID_START_TIME,Constants.INVALID_END_TIME);
        assertEquals(0, drivers.size());
    }

    @Test
    public void getDriversThathHaveReservationInPeriod_shouldBe2(){
        List<Driver> drivers = driverService.getDriversThatHaveReservationInPeriod(Constants.VALID_START_TIME,Constants.INVALID_END_TIME);
        assertEquals(2, drivers.size());
    }

    @Test
    public void getSuitedDriver_shouldBeTestDriver1(){
        RideDTO rideDTO = new RideDTO();
        rideDTO.setVehicleType(VehicleType.BASIC.ordinal());
        rideDTO.setDeniedDrivers(new ArrayList<>());
        rideDTO.setStartTime(Constants.INVALID_START_TIME);
        rideDTO.setEndTime(Constants.INVALID_END_TIME);
        rideDTO.setStops("blba,14,13;caca,55,66");
        Constants.testDriver.setPosition(new Coordinate("blizu",14f,13f));
        Constants.testDriver2.setPosition(new Coordinate("daleko",56f,13f));
        Constants.testDriver3.setPosition(new Coordinate("daleko2",36f,13f));
        Constants.testDriver4.setPosition(new Coordinate("daleko3",36f,55f));

        Driver driver = driverService.getSuitedDriver(rideDTO);
        assertEquals(driver.getId(),Constants.testDriver.getId());
    }

    @Test
    public void getSuitedDriver_shouldBeDriver2(){
        RideDTO rideDTO = new RideDTO();
        rideDTO.setVehicleType(VehicleType.BASIC.ordinal());
        rideDTO.setDeniedDrivers(new ArrayList<>());
        rideDTO.setStartTime(Constants.INVALID_START_TIME);
        rideDTO.setEndTime(Constants.INVALID_END_TIME);
        rideDTO.setStops("blba,14,13;caca,55,66");
        Constants.testDriver.setPosition(new Coordinate("daleko2",36f,13f));
        Constants.testDriver2.setPosition(new Coordinate("blizu",14f,13f));
        Constants.testDriver3.setPosition(new Coordinate("daleko2",36f,13f));
        Constants.testDriver4.setPosition(new Coordinate("daleko3",36f,55f));

        Driver driver = driverService.getSuitedDriver(rideDTO);
        assertEquals(driver,Constants.testDriver2);
    }

    @Test
    public void getClosestDriver_shouldBeTestDriver2(){
        List<Driver> drivers = new ArrayList<>();
        Constants.testDriver.setPosition(new Coordinate("daleko2",36f,13f));
        Constants.testDriver2.setPosition(new Coordinate("blizu",14f,13f));
        Constants.testDriver3.setPosition(new Coordinate("daleko2",36f,13f));
        Constants.testDriver4.setPosition(new Coordinate("daleko3",36f,55f));
        drivers.add(Constants.testDriver);
        drivers.add(Constants.testDriver2);
        drivers.add(Constants.testDriver3);
        drivers.add(Constants.testDriver4);

        Coordinate coordinate = new Coordinate("blabla", 14f,13f);

        Driver driver = driverService.getClosestDriver(drivers,coordinate);
        assertEquals(driver, Constants.testDriver2);
    }

    @Test
    public void getFirstToFinishDriver_shouldBeTestDriver1(){
        List<Driver> drivers = new ArrayList<>();
        Coordinate coordinate1 = new Coordinate("blizu",14f,13f);
        Coordinate coordinate2 = new Coordinate("daleko2",36f,13f);
        Coordinate coordinate3 = new Coordinate("daleko3",36f,55f);
        Coordinate coordinate4 = new Coordinate("daleko4",14f,14f);
        Constants.testDriver.setPosition(coordinate1);
        Constants.testDriver2.setPosition(coordinate2);
        Constants.testDriver3.setPosition(coordinate3);
        drivers.add(Constants.testDriver);
        drivers.add(Constants.testDriver2);
        drivers.add(Constants.testDriver3);
        Constants.testFare1.setStops(Arrays.asList(coordinate4));
        Constants.testFare2.setStops(Arrays.asList(coordinate3));
        Constants.testFare3.setStops(Arrays.asList(coordinate2));

        Driver driver = driverService.getFirstToFinishDriver(drivers);
        assertEquals(Constants.testDriver.getId(), driver.getId());


    }

    @Test
    public void getFirstToFinishDriver_shouldBeTestDriver2(){
        List<Driver> drivers = new ArrayList<>();
        Coordinate coordinate1 = new Coordinate("blizu",14f,13f);
        Coordinate coordinate2 = new Coordinate("daleko2",36f,13f);
        Coordinate coordinate3 = new Coordinate("daleko3",36f,55f);
        Coordinate coordinate4 = new Coordinate("blizu",14f,14f);
        Constants.testDriver.setPosition(coordinate2);
        Constants.testDriver2.setPosition(coordinate1);
        Constants.testDriver3.setPosition(coordinate3);
        drivers.add(Constants.testDriver);
        drivers.add(Constants.testDriver2);
        drivers.add(Constants.testDriver3);
        Constants.testFare1.setStops(Arrays.asList(coordinate3));
        Constants.testFare2.setStops(Arrays.asList(coordinate4));
        Constants.testFare3.setStops(Arrays.asList(coordinate2));

        Driver driver = driverService.getFirstToFinishDriver(drivers);
        assertEquals(Constants.testDriver2.getId(), driver.getId());


    }

    @Test
    public void changeDriverStatus_shouldBeAvailable(){
        Constants.testDriver.setDriverStatus(DriverStatus.UNAVAILABLE);
        driverService.changeDriverStatus(Constants.testDriver,true);

        assertEquals(DriverStatus.AVAILABLE, Constants.testDriver.getDriverStatus());
    }




    @Test
    public void getClosestDrive_shouldBeDriver2(){

        List<Driver> drivers = new ArrayList<>();
        Constants.testDriver.setPosition(new Coordinate("dskand", 13f,15f));
        Constants.testDriver2.setPosition(new Coordinate("dskand", 13f,26f));
        Constants.testDriver3.setPosition(new Coordinate("dskand", 44f,45f));
        Constants.testDriver4.setPosition(new Coordinate("dskand", 20f,65f));
        drivers.add(Constants.testDriver);
        drivers.add(Constants.testDriver2);
        drivers.add(Constants.testDriver3);
        drivers.add(Constants.testDriver4);

        Coordinate coordinate = new Coordinate("pocetak", 14f, 16f);

        Driver ognjen = driverService.getClosestDriver(drivers,coordinate);

        assertEquals( Constants.testDriver.getId() , ognjen.getId());
    }

    @Test
    public void changeDriverStatus_shouldBeUnavailable(){

        Constants.testDriver.setDriverStatus(DriverStatus.AVAILABLE);
        assertEquals(DriverStatus.AVAILABLE, Constants.testDriver.getDriverStatus());

        driverService.changeDriverStatus(Constants.testDriver , false);
        assertEquals(DriverStatus.UNAVAILABLE, Constants.testDriver.getDriverStatus());
    }

}
