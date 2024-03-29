package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.*;
import com.example.nwtktsapi.repository.DriverRepository;
import com.example.nwtktsapi.repository.RideRepository;
import com.example.nwtktsapi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private RideService rideService;

    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void setRideService(RideService rideService) {
        this.rideService = rideService;
    }

    public Driver save(Driver driver){
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers(){
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driver.orElse(null);
    }

    public Driver getDriverByEmail(String email){
        Optional<Driver> driver = driverRepository.findByEmail(email);
        return driver.orElse(null);
    }

    public List<Driver> getAvailableDrivers(int type) {
        return driverRepository.getAvailableDrivers(VehicleType.values()[type]);
    }

    public List<Driver> getDrivingDrivers(int type) {
        return driverRepository.getDrivingDrivers(VehicleType.values()[type]);
    }
    public List<Driver> getDriversThatHaveReservationInPeriod(LocalDateTime startTime,LocalDateTime endTime){
        List<Fare> reservationsInPeriod = rideRepository.getReservationsInPeriod(true,startTime,endTime);
        List<Driver> res=  new ArrayList<>();
        for (Fare fare:reservationsInPeriod) {
            res.add( fare.getDriver() );
        }
        return res;
    }

    public Driver getSuitedDriver(RideDTO rideDTO) {
        List<Driver> available = getAvailableDrivers(rideDTO.getVehicleType());
        List<Driver> reservedDriversInThatPeriod = getDriversThatHaveReservationInPeriod(rideDTO.getStartTime(),rideDTO.getEndTime());

        available.removeIf( driver -> rideDTO.getDeniedDrivers().contains(driver.getId()));
        available.removeIf(reservedDriversInThatPeriod::contains);
        if (available.size() == 0) {

            List<Driver> driving = getDrivingDrivers(rideDTO.getVehicleType());
            driving.removeIf( driver -> rideDTO.getDeniedDrivers().contains(driver.getId()));
            driving.removeIf(reservedDriversInThatPeriod::contains);
            if (driving.size() == 0) {
                return null;
            }
            else {
                return getFirstToFinishDriver(driving);
            }
        }
        else {
            return getClosestDriver(available, rideDTO.getLocations().get(0));
        }
    }


    public Driver getClosestDriver(List<Driver> drivers, Coordinate start) {
        Driver closestDriver = drivers.get(0);
        double minDistance = Double.MAX_VALUE;
        for (Driver d : drivers) {
            double distance = countDistance(d.getPosition(), start);
            if (minDistance > distance) {
                closestDriver = d;
                minDistance = distance;
            }
        }
        return  closestDriver;
    }

    public Driver getFirstToFinishDriver(List<Driver> drivers) {
        Driver firstToFinish = drivers.get(0);
        double minDistance = Double.MAX_VALUE;
        for (Driver d: drivers) {
            Fare currentFare = rideService.getCurrentDriverFare(d.getId());
            Coordinate lastStation = currentFare.getStops().get(currentFare.getStops().size()-1);
            double distance = countDistance(d.getPosition(), lastStation);
            if (minDistance > distance) {
                firstToFinish = d;
                minDistance = distance;
            }
        }
        return firstToFinish;
    }

    public double countDistance(Coordinate driverLocation, Coordinate stationLocation) {
        double x1 = driverLocation.getLatitude();
        double y1 = driverLocation.getLongitude();
        double x2 = stationLocation.getLatitude();
        double y2 = stationLocation.getLongitude();
        return Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public void changeDriverStatus(Driver driver, boolean isAvailable){
        if(isAvailable){
            driver.setDriverStatus(DriverStatus.AVAILABLE);
        }
        else{
            driver.setDriverStatus(DriverStatus.UNAVAILABLE);
        }
        driverRepository.save(driver);

    }
}
