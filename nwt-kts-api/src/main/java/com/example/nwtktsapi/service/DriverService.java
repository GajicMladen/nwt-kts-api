package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.dto.StationDTO;
import com.example.nwtktsapi.model.Coordinates;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.repository.DriverRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAvailableDrivers() {
        return driverRepository.getAvailableDrivers();
    }

    public List<Driver> getDrivingDrivers() {
        return driverRepository.getDrivingDrivers();
    }

    public Driver getSuitedDriver(RideDTO rideDTO) {
        //TODO: Pogledati rezervacije
        List<Driver> available = getAvailableDrivers();
        if (available.size() == 0) {
            List<Driver> driving = getDrivingDrivers();
            if (driving.size() == 0) {
                return null;
            }
            else {
                //TODO: Videti koji vozac je najblizi krajnjoj lokaciji trenutne voznje
                return driving.get(0);
            }
        }
        else {
            return getClosestDriver(available, rideDTO.getLocations().get(0));
        }
    }

    public Driver getClosestDriver(List<Driver> drivers, StationDTO start) {
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

    public double countDistance(Coordinates driverLocation, StationDTO startLocation) {
        double x1 = driverLocation.getLatitude();
        double y1 = driverLocation.getLongitude();
        double x2 = startLocation.getLat();
        double y2 = startLocation.getLng();
        return Math.sqrt( Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
}
