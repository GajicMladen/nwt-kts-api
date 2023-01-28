package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.LogoutDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverTimesheet;
import com.example.nwtktsapi.repository.DriverTimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverTimesheetService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverTimesheetRepository driverTimesheetRepository;

    public boolean canLogin(Long driverId) {
        if (isOverworked(driverId)){
            return false;
        }
        driverTimesheetRepository.save(new DriverTimesheet(driverService.getDriverById(driverId), "login", LocalDateTime.now()));
        return true;
    }

    private boolean isOverworked(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayBefore = now.minusHours(24);
        List<DriverTimesheet> timesheets = driverTimesheetRepository.findByDriverIdAndTimeBetween(id, dayBefore, now);
        if (timesheets.size() == 0) {
            return false;
        } else {
            long seconds = getSecondsWorked(timesheets);
            System.out.println("SECONDS: " + seconds);
            return seconds >= 28800;
        }
    }

    private long getSecondsWorked(List<DriverTimesheet> timesheets) {
        long seconds = 0L;
        //if (timesheets.size() % 2 == 0) {
        if (timesheets.get(0).getType().equals("login")) {
            seconds = getSecondsStartLogin(timesheets);
        } else if (timesheets.get(0).getType().equals("logout")) {
            seconds = getSecondsStartLogout(timesheets);
        }
        return seconds;
    }

    private long getSecondsStartLogin(List<DriverTimesheet> timesheets) {
        long seconds = 0L;
        for (int i = 0; i < timesheets.size(); i+=2) {
            DriverTimesheet current = timesheets.get(i);
            try {
                DriverTimesheet next = timesheets.get(i+1);
                Duration duration = Duration.between(current.getTime(), next.getTime());
                seconds += duration.getSeconds();
            } catch (IndexOutOfBoundsException e) {
                Duration duration = Duration.between(current.getTime(), LocalDateTime.now());
                seconds += duration.getSeconds();
                return seconds;
            }
        }
        return seconds;
    }

    private long getSecondsStartLogout(List<DriverTimesheet> timesheets) {
        long seconds = 0L;
        LocalDateTime loginTime = LocalDateTime.now().minusHours(24);
        LocalDateTime logoutTime = LocalDateTime.now();
        for (int i = 0; i < timesheets.size(); i+=2) {
            try {
                DriverTimesheet logout = timesheets.get(i);
                logoutTime = logout.getTime();
            } catch (IndexOutOfBoundsException e) {
                logoutTime = LocalDateTime.now();
            }
            Duration duration = Duration.between(loginTime, logoutTime);
            seconds += duration.getSeconds();
            try {
                DriverTimesheet login = timesheets.get(i+1);
                loginTime = login.getTime();
            } catch (IndexOutOfBoundsException e) {
                return seconds;
            }
        }
        return seconds;
    }

    public void logout(Driver driver) {
        driverTimesheetRepository.save(new DriverTimesheet(driver, "logout", LocalDateTime.now()));
    }

    private boolean isLogged(long driverId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayBefore = now.minusHours(24);
        List<DriverTimesheet> timesheets = driverTimesheetRepository.findByDriverIdAndTimeBetween(driverId, dayBefore, now);
        return timesheets.size() != 0 && timesheets.get(timesheets.size() - 1).getType().equals("login");
    }

    private void sendLogout(long driverId) {
        String url = "http://localhost:3000/logout-driver";
        LogoutDTO logoutDTO = new LogoutDTO(String.valueOf(driverId));
        HttpEntity<LogoutDTO> request = new HttpEntity<>(logoutDTO);
        restTemplate.postForObject(url, request, Void.class);
    }

    @Scheduled(fixedRate = 60000) // 10 minutes
    public void checkLoggedInDrivers() {
        System.out.println("Checking logged drivers");
        for (Driver driver : driverService.getAllDrivers()) {
            if (isLogged(driver.getId()) && isOverworked(driver.getId())) {
                System.out.println("Izloguj vozaca: " + driver.getEmail());
                sendLogout(driver.getId());
            }
        }
    }
}
