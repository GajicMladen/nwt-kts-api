package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Coordinate;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.VehicleType;
import com.example.nwtktsapi.repository.DriverRepository;
import com.example.nwtktsapi.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideService rideService;

    @Autowired
    private EmailService emailService;


    public List<Driver> getAvailableDrivers(int type) {
        return driverRepository.getAvailableDrivers(VehicleType.values()[type]);
    }

    public List<Driver> getDrivingDrivers(int type) {
        return driverRepository.getDrivingDrivers(VehicleType.values()[type]);
    }

    public Driver getSuitedDriver(RideDTO rideDTO) {
        //TODO: Pogledati rezervacije
        List<Driver> available = getAvailableDrivers(rideDTO.getVehicleType());
        if (available.size() == 0) {
            List<Driver> driving = getDrivingDrivers(rideDTO.getVehicleType());
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

    public void notifyNewRide(Driver driver, RideDTO rideDTO) {
        emailService.sendNewRideToDriver(driver, rideDTO.getLocationsNames(), rideDTO.getPrice());
    }
}
