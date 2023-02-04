package com.example.nwtktsapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.RideService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.name=application-test"})
@ActiveProfiles("test")
public class RideControllerTest {


    @Autowired
    private UserService userService;

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;


    private Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        // Set up necessary data for the tests
    }
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeClientInRide() {
        User user = userService.getUserById(1L);
        user.setInRide(true);
        userService.save(user);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("/api/ride/isClientInRide/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Boolean>() {
                });

        Boolean isClientInRide = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true,isClientInRide);
    }
    
    @Test
    public void shouldNotBeClientInRide() {
        User user = userService.getUserById(2L);
        user.setInRide(false);
        userService.save(user);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange("/api/ride/isClientInRide/2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Boolean>() {
                });

        Boolean isClientInRide = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(false,isClientInRide);
    }

    @Test
    public void orderRideSuccessful() throws Exception {
        // Set up a user with sufficient tokens
        User client = Constants.testClient;
        client.setTokens(1000);
        client = userService.save(client);

        RideDTO rideDTO = Constants.RIDE_DTO;
        rideDTO.setClientId(client.getId());
        rideDTO.setSplitFare(new String[]{});
        rideDTO.setPrice(100);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);
        // Make the request
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("c","testpass")
                .exchange(
                "/api/ride/order",
                HttpMethod.POST,
                entity,
                String.class
        );
        // Check the response status code and body
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(gson.toJson("Voznja je porucena!"), response.getBody());
    }
//
    @Test
    public void orderRide_NotEnoughTokens() throws Exception {
        // Set up a user with sufficient tokens
        User client = Constants.testClient;
        client.setTokens(50);
        client = userService.save(client);

        RideDTO rideDTO = Constants.RIDE_DTO;
        rideDTO.setClientId(client.getId());
        rideDTO.setPrice(100);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);
        // Make the request
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("c","testpass")
                .exchange(
                        "/api/ride/order",
                        HttpMethod.POST,
                        entity,
                        String.class
                );
        // Check the response status code and body
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(gson.toJson(new ErrMsg("Nemate dovoljno tokena")), response.getBody());
    }

    @Test
    public void orderRide_NotEnoughTokensWhenSplitFare() throws Exception {
        // Set up a user with sufficient tokens
        User client = Constants.testClient;
        client.setTokens(10);
        client = userService.save(client);

        RideDTO rideDTO = Constants.RIDE_DTO;
        rideDTO.setClientId(client.getId());
        rideDTO.setSplitFare(new String[]{"a","b"});
        rideDTO.setPrice(100);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);
        // Make the request
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("c","testpass")
                .exchange(
                        "/api/ride/order",
                        HttpMethod.POST,
                        entity,
                        String.class
                );
        // Check the response status code and body
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(gson.toJson(new ErrMsg("Nemate dovoljno tokena,iako se voznja deli...")), response.getBody());
    }

    @Test
    public void acceptRide_OK(){
        // Set up a user with sufficient tokens
        Driver driver = Constants.testDriver;
        driver.setVehicle(Constants.testVehicle);
        driver = driverService.save(driver);

        RideDTO rideDTO = Constants.RIDE_DTO;
        rideDTO.setDriverId(driver.getId());
        rideDTO.setClientId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);
        // Make the request
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("d","testpass")
                .exchange(
                        "/api/ride/acceptRide",
                        HttpMethod.POST,
                        entity,
                        String.class
                );
        // Check the response status code and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gson.toJson(driver.getVehicle().getPlateNumber()), response.getBody());
    }

    @Test
    public void acceptRide_ErrorWhenNoDriver(){
        RideDTO rideDTO = Constants.RIDE_DTO;
        rideDTO.setClientId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("d","testpass")
                .exchange(
                        "/api/ride/acceptRide",
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        assertEquals(500, response.getStatusCode().value());
    }


    @Test
    public void finishRide_shouldBeOK(){
        RideDTO rideDTO = Constants.RIDE_DTO;
        Driver driver = Constants.testDriver;
        Client client = Constants.testClient;

        driver.setDriverStatus(DriverStatus.DRIVING);
        driver.setVehicle(Constants.testVehicle);
        driver.setInRide(true);
        client.setInRide(true);
        client = (Client) userService.save(client);
        driver = (Driver) userService.save(driver);
        rideDTO.setClientId(client.getId());
        rideDTO.setDriverId(driver.getId());
        Fare fare = rideService.save(rideDTO,driver);
        rideDTO.setRideId( fare.getFareID() );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RideDTO> entity = new HttpEntity<>(rideDTO, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("d","testpass")
                .exchange(
                        "/api/ride/finishRide",
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        assertEquals(HttpStatus.OK,response.getStatusCode());

        client = (Client) userService.getUserById(client.getId());
        driver = (Driver) userService.getUserById(driver.getId());

        assertFalse(client.isInRide());
        assertFalse(driver.isInRide());
        assertEquals(DriverStatus.AVAILABLE,driver.getDriverStatus());
    }

    @Test
    public void activeRideForDriver_shouldBeOK(){
        Fare fare = Constants.testFare1;
        Driver driver = Constants.testDriver;
        Client client = Constants.testClient;
        driver.setDriverStatus(DriverStatus.DRIVING);
        driver.setVehicle(Constants.testVehicle);
        driver.setInRide(true);
        client.setInRide(true);

        client = (Client) userService.save(client);
        driver = (Driver) userService.save(driver);

        fare.setDriver(driver);
        fare.setClients(Arrays.asList(client));
        fare.setDone(false);
        fare.setActive(true);
        rideService.saveFare(fare);

        ResponseEntity<String> response = restTemplate
                .exchange(
                        "/api/ride/activeRideForDriver/"+driver.getId().toString(),
                        HttpMethod.GET,
                        null,
                        String.class
                );

        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

}
