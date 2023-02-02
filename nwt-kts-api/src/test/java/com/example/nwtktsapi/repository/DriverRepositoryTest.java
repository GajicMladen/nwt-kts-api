package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.Vehicle;
import com.example.nwtktsapi.model.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @Transactional
    public void getAvailableDrivers(){
        Constants.testDriver.setActive(true);
        Constants.testDriver.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle.setDriver(Constants.testDriver);
        vehicleRepository.save(Constants.testVehicle);
        Constants.testDriver.setVehicle(Constants.testVehicle);

        Constants.testDriver2.setActive(true);
        Constants.testDriver2.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle2.setDriver(Constants.testDriver2);
        vehicleRepository.save(Constants.testVehicle2);
        Constants.testDriver2.setVehicle(Constants.testVehicle2);

        Constants.testDriver3.setActive(true);
        Constants.testDriver3.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle3.setDriver(Constants.testDriver3);
        vehicleRepository.save(Constants.testVehicle3);
        Constants.testDriver3.setVehicle(Constants.testVehicle3);

        Constants.testDriver4.setActive(true);
        Constants.testDriver4.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle4.setDriver(Constants.testDriver4);
        vehicleRepository.save(Constants.testVehicle4);
        Constants.testDriver4.setVehicle(Constants.testVehicle4);

        driverRepository.save(Constants.testDriver);
        driverRepository.save(Constants.testDriver2);
        driverRepository.save(Constants.testDriver3);
        driverRepository.save(Constants.testDriver4);

        List<Driver> drivers = driverRepository.getAvailableDrivers(Constants.testVehicle.getType());
        assertEquals(4 , drivers.size());
    }

    @Test
    @Transactional
    public void getAvailableDrivers_shouldBe3(){
        Constants.testDriver.setActive(true);
        Constants.testDriver.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle.setDriver(Constants.testDriver);
        Vehicle vehicle = vehicleRepository.save(Constants.testVehicle);
        Constants.testDriver.setVehicle(vehicle);

        Constants.testDriver2.setActive(true);
        Constants.testDriver2.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle2.setDriver(Constants.testDriver2);
        Vehicle vehicle2 = vehicleRepository.save(Constants.testVehicle2);
        Constants.testDriver2.setVehicle(vehicle2);

        Constants.testDriver3.setActive(true);
        Constants.testDriver3.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle3.setDriver(Constants.testDriver3);
        Vehicle vehicle3 = vehicleRepository.save(Constants.testVehicle3);
        Constants.testDriver3.setVehicle(vehicle3);

        Constants.testDriver4.setActive(false);
        Constants.testDriver4.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle4.setDriver(Constants.testDriver4);
        Vehicle vehicle4 = vehicleRepository.save(Constants.testVehicle4);
        Constants.testDriver4.setVehicle(vehicle4);

        driverRepository.save(Constants.testDriver);
        driverRepository.save(Constants.testDriver2);
        driverRepository.save(Constants.testDriver3);
        driverRepository.save(Constants.testDriver4);

        List<Driver> drivers = driverRepository.getAvailableDrivers(Constants.testVehicle.getType());
        assertEquals(3 , drivers.size());
    }

    @Test
    @Transactional
    public void getAvailableDrivers_shouldBe0(){
        List<Driver> drivers = driverRepository.getAvailableDrivers(VehicleType.BASIC);
        assertEquals(0, drivers.size());
    }


    @Test
    @Transactional
    public void getAvailableDrivers_shouldBe1(){
        Constants.testDriver3.setActive(true);
        Constants.testDriver3.setDriverStatus(DriverStatus.AVAILABLE);
        Constants.testVehicle3.setDriver(Constants.testDriver3);
        Vehicle vehicle3 = vehicleRepository.save(Constants.testVehicle);
        Constants.testDriver3.setVehicle(vehicle3);
        List<Driver> drivers = driverRepository.getAvailableDrivers(VehicleType.BASIC);
        assertEquals(0, drivers.size());
    }

    @Test
    public void getDrivingDrivers_shouldBe0(){
        List<Driver> drivers = driverRepository.getDrivingDrivers(VehicleType.BASIC);
        assertEquals(0, drivers.size());
    }

    @Test
    @Transactional
    public void getDrivingDrivers_shouldBe1(){
        Constants.testDriver3.setActive(true);
        Constants.testDriver3.setDriverStatus(DriverStatus.DRIVING);
        Constants.testVehicle3.setDriver(Constants.testDriver3);
        Constants.testVehicle3.setType(VehicleType.BASIC);
        Vehicle vehicle3 = vehicleRepository.save(Constants.testVehicle3);
        Constants.testDriver3.setVehicle(vehicle3);
        driverRepository.save(Constants.testDriver3);

        List<Driver> drivers = driverRepository.getDrivingDrivers(VehicleType.BASIC);
        assertEquals(1, drivers.size());
    }

    @Test
    @Transactional
    public void getDrivingDrivers_shouldBe2(){
        Constants.testDriver3.setActive(true);
        Constants.testDriver3.setDriverStatus(DriverStatus.DRIVING);
        Constants.testVehicle3.setDriver(Constants.testDriver3);
        Constants.testVehicle3.setType(VehicleType.BASIC);
        Vehicle vehicle3 = vehicleRepository.save(Constants.testVehicle3);
        Constants.testDriver3.setVehicle(vehicle3);
        driverRepository.save(Constants.testDriver3);

        Constants.testDriver2.setActive(true);
        Constants.testDriver2.setDriverStatus(DriverStatus.DRIVING);
        Constants.testVehicle2.setDriver(Constants.testDriver3);
        Constants.testVehicle2.setType(VehicleType.BASIC);
        Vehicle vehicle2 = vehicleRepository.save(Constants.testVehicle2);
        Constants.testDriver2.setVehicle(vehicle2);
        driverRepository.save(Constants.testDriver2);

        List<Driver> drivers = driverRepository.getDrivingDrivers(VehicleType.BASIC);
        assertEquals(2, drivers.size());
    }
}
