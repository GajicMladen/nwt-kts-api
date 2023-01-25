package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.ReportDTO;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.ReportType;
import com.example.nwtktsapi.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    private RideRepository rideRepository;

    public Map<String, Object> delegateReport(ReportDTO reportDTO) {
        LocalDateTime startDateTime = parseStringToLocalDateTime(reportDTO.getStart());
        LocalDateTime endDateTime = parseStringToLocalDateTime(reportDTO.getEnd());
        return getReport(startDateTime, endDateTime, reportDTO.getReportType(), reportDTO.getUserId());
    }

    private Map<String, Object> getReport(LocalDateTime start, LocalDateTime end, ReportType type, Long userId) {
        HashMap<String, Integer> ridePerDay = new HashMap<>();
        HashMap<String, Float> kilometersPerDay = new HashMap<>();
        HashMap<String, Float> moneyPerDay = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (start.isBefore(end.plusDays(1))) {
            LocalDateTime startOfDay = start;
            LocalDateTime endOfDay = start.plusHours(23).plusMinutes(59).plusSeconds(59);
            List<Float> results = new ArrayList<>();
            switch (type) {
                case ONE_CLIENT:
                    results = getClientResults(userId, startOfDay, endOfDay);
                    break;
                case ONE_DRIVER:
                    results = getDriverResults(userId, startOfDay, endOfDay);
                    break;
                case ALL_CLIENTS:
                    results = getClientsResults(startOfDay, endOfDay);
                    break;
                case ALL_DRIVERS:
                    results = getDriversResults(startOfDay, endOfDay);
                    break;
                default:
                    break;
            }
            ridePerDay.put(start.format(formatter), Math.round(results.get(0)));
            kilometersPerDay.put(start.format(formatter), results.get(1));
            moneyPerDay.put(start.format(formatter), results.get(2));
            start = start.plusDays(1);
        }
        Map<String, Integer> sortedRidePerDay = new TreeMap<>(ridePerDay);
        Map<String, Float> sortedKilometersPerDay = new TreeMap<>(kilometersPerDay);
        Map<String, Float> sortedMoneyPerDay = new TreeMap<>(moneyPerDay);
        Map<String, Object> data = new HashMap<>();
        data.put("ridesPerDay", sortedRidePerDay);
        data.put("kilometersPerDay", sortedKilometersPerDay);
        data.put("moneyPerDay", sortedMoneyPerDay);
        return data;
    }

    private List<Float> getClientResults(Long id, LocalDateTime start, LocalDateTime end) {
        List<Float> results = new ArrayList<>();
        List<Fare> rides = rideRepository.findFaresByClientIdAndStartTimeBetween(id, start, end);
        float kilometers = 0;
        float money = 0;
        for (Fare ride: rides) {
            kilometers += ride.getDistance()/1000;
            money += ride.getPrice()/ride.getClients().size();
        }
        results.add((float) rides.size());
        results.add(kilometers);
        results.add(money);
        return results;
    }

    private List<Float> getDriverResults(Long id, LocalDateTime start, LocalDateTime end) {
        List<Float> results = new ArrayList<>();
        List<Fare> rides = rideRepository.findByDriver_IdAndStartTimeBetween(id, start, end);
        float kilometers = 0;
        float money = 0;
        for (Fare ride: rides) {
            kilometers += ride.getDistance()/1000;
            money += ride.getPrice();
        }
        results.add((float) rides.size());
        results.add(kilometers);
        results.add(money);
        return results;
    }

    private List<Float> getClientsResults(LocalDateTime start, LocalDateTime end) {
        List<Float> results = new ArrayList<>();
        List<Fare> rides = rideRepository.findByStartTimeBetween(start, end);
        float kilometers = 0;
        float money = 0;
        for (Fare ride: rides) {
            kilometers += (ride.getDistance()/1000)*ride.getClients().size();
            money += ride.getPrice();
        }
        results.add((float) rides.size());
        results.add(kilometers);
        results.add(money);
        return results;
    }

    private List<Float> getDriversResults(LocalDateTime start, LocalDateTime end) {
        List<Float> results = new ArrayList<>();
        List<Fare> rides = rideRepository.findByStartTimeBetween(start, end);
        float kilometers = 0;
        float money = 0;
        for (Fare ride: rides) {
            kilometers += ride.getDistance()/1000;
            money += ride.getPrice();
        }
        results.add((float) rides.size());
        results.add(kilometers);
        results.add(money);
        return results;
    }

    private LocalDateTime parseStringToLocalDateTime(String date) {
        String[] tokens = date.split(" ");
        int year = Integer.parseInt(tokens[3]);
        Month month = getMonthFromString(tokens[1]);
        int day = Integer.parseInt(tokens[2]);
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

    private Month getMonthFromString(String month) {
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("Jan", "JANUARY");
        monthMap.put("Feb", "FEBRUARY");
        monthMap.put("Mar", "MARCH");
        monthMap.put("Apr", "APRIL");
        monthMap.put("May", "MAY");
        monthMap.put("Jun", "JUNE");
        monthMap.put("Jul", "JULY");
        monthMap.put("Aug", "AUGUST");
        monthMap.put("Sep", "SEPTEMBER");
        monthMap.put("Oct", "OCTOBER");
        monthMap.put("Nov", "NOVEMBER");
        monthMap.put("Dec", "DECEMBER");
        return Month.valueOf(monthMap.get(month));
    }
}
