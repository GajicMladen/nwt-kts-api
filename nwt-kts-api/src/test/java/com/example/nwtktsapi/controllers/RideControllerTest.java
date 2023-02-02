package com.example.nwtktsapi.controllers;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.DriverService;
import com.example.nwtktsapi.service.RideService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}
