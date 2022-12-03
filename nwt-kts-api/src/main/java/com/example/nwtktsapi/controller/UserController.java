package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.TransformToDTOService;
import com.example.nwtktsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
