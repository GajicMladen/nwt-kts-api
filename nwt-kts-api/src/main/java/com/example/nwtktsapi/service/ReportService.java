package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.ReportDTO;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ReportService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserService userService;

    public Map<String, Object> delegateReport(ReportDTO reportDTO) {
        LocalDateTime startDateTime = parseStringToLocalDateTime(reportDTO.getStart());
        LocalDateTime endDateTime = parseStringToLocalDateTime(reportDTO.getEnd());
        switch (reportDTO.getReportType()) {
            case ONE_CLIENT:
                return getClientReports(startDateTime, endDateTime, userService.findById(reportDTO.getUserId()).get());
            case ONE_DRIVER:
                return getDriverReports(startDateTime, endDateTime, userService.findById(reportDTO.getUserId()).get());
            case ALL_CLIENTS:
                break;
            case ALL_DRIVERS:
                break;
            default:
                break;
        }
        return null;
    }

    private Map<String, Object> getClientReports(LocalDateTime start, LocalDateTime end, User client) {
        HashMap<String, Integer> ridePerDay = new HashMap<>();
        HashMap<String, Float> kilometersPerDay = new HashMap<>();
        HashMap<String, Float> moneyPerDay = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (start.isBefore(end.plusDays(1))) {
            LocalDateTime startOfDay = start;
            LocalDateTime endOfDay = start.plusHours(23).plusMinutes(59).plusSeconds(59);
            List<Fare> rides = rideRepository.findFaresByClientIdAndStartTimeBetween(client.getId(), startOfDay, endOfDay);
            float kilometers = 0;
            float money = 0;
            for (Fare ride: rides) {
                kilometers += ride.getDistance()/1000;
                money += ride.getPrice()/ride.getClients().size();
            }
            ridePerDay.put(start.format(formatter), rides.size());
            kilometersPerDay.put(start.format(formatter), kilometers);
            moneyPerDay.put(start.format(formatter), money);
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

    private Map<String, Object> getDriverReports(LocalDateTime start, LocalDateTime end, User driver) {
        HashMap<String, Integer> ridePerDay = new HashMap<>();
        HashMap<String, Float> kilometersPerDay = new HashMap<>();
        HashMap<String, Float> moneyPerDay = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (start.isBefore(end.plusDays(1))) {
            LocalDateTime startOfDay = start;
            LocalDateTime endOfDay = start.plusHours(23).plusMinutes(59).plusSeconds(59);
            List<Fare> rides = rideRepository.findByDriver_IdAndStartTimeBetween(driver.getId(), startOfDay, endOfDay);
            float kilometers = 0;
            float money = 0;
            for (Fare ride: rides) {
                kilometers += ride.getDistance()/1000;
                money += ride.getPrice();
            }
            ridePerDay.put(start.format(formatter), rides.size());
            kilometersPerDay.put(start.format(formatter), kilometers);
            moneyPerDay.put(start.format(formatter), money);
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
