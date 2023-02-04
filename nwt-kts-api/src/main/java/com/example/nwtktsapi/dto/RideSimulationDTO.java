package com.example.nwtktsapi.dto;

import java.util.List;

public class RideSimulationDTO {

    private String driver;

    private List<List<Float>> values;

    public RideSimulationDTO() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public List<List<Float>> getValues() {
        return values;
    }

    public void setValues(List<List<Float>> values) {
        this.values = values;
    }
}
