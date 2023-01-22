package com.example.nwtktsapi.controller;


import com.example.nwtktsapi.dto.UserDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.TransformToDTOService;
import com.example.nwtktsapi.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "api/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransformToDTOService transformToDTOService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Object>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<Object> usersDTO = transformToDTOService.transformToDTOList(users);

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "getUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO(user);

        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping(value ="clients")
    public ResponseEntity<?> getAllClientsPagination(@RequestParam int page, @RequestParam int size) {
        List<User> clients = userService.getAllClientsPagination(page, size);
        //List<User> clients = userService.getAllClientsPagination();
        List<UserDTO> clientsDTO = new ArrayList<>();
        for (User u : clients) {
            clientsDTO.add(new UserDTO(u));
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "clients/count")
    public ResponseEntity<?> getClientsCount() {
        int count = userService.getClientsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "drivers")
    public ResponseEntity<?> getAllDriversPagination(@RequestParam int page, @RequestParam int size) {
        List<User> drivers = userService.getAllDriversPagination(page, size);
        //List<User> drivers = userService.getAllDriversPagination();
        List<UserDTO> driversDTO = new ArrayList<>();
        for (User u : drivers) {
            driversDTO.add(new UserDTO(u));
        }
        return new ResponseEntity<>(driversDTO, HttpStatus.OK);
    }

    @GetMapping(value = "drivers/count")
    public ResponseEntity<?> getDriversCount() {
        int count = userService.getDriversCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "block")
    public ResponseEntity<?> blockUser(@RequestParam Long id) {
        User user = this.userService.blockUser(id);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }


}
