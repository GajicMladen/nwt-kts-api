package com.example.nwtktsapi.service;

import com.example.nwtktsapi.model.VehiclePrice;
import com.example.nwtktsapi.model.VehicleType;
import com.example.nwtktsapi.repository.VehiclePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiclePriceService {

    @Autowired
    private VehiclePriceRepository vehiclePriceRepository;

    public int getTypePrice(VehicleType type) {
        VehiclePrice vehiclePrice = vehiclePriceRepository.findByType(type);
        return vehiclePrice.getPrice();
    }
}
