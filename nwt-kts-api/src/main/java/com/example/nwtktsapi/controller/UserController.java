package com.example.nwtktsapi.controller;


import java.security.Principal;
import com.example.nwtktsapi.dto.UserDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nwtktsapi.dto.ChangeUserDataDTO;
import com.example.nwtktsapi.dto.ResetPasswordDTO;
import com.example.nwtktsapi.dto.UserDTO;
import com.example.nwtktsapi.model.DriverChangeRequest;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.DriverChangeService;
import com.example.nwtktsapi.service.TransformToDTOService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "api/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransformToDTOService transformToDTOService;
    
    @Autowired
    private DriverChangeService driverChangeService;

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
    
    @PostMapping(value= "resetPassword")
    public ResponseEntity<?> resetPassword(Principal user, @RequestBody ResetPasswordDTO resetDTO){
		Gson gson = new Gson();
		
		if(!resetDTO.validate())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("Uneti podaci su neispravni!")));
		
		User u = userService.findByEmail(user.getName());
		userService.setEncryptedPassword(u, resetDTO.getNewPassword());
		userService.save(u);
		
		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping(value = "changeUserData")
    public ResponseEntity<?> changeUserData(Principal user, @RequestBody ChangeUserDataDTO dataDTO){
    	Gson gson = new Gson();
    	User u = userService.findByEmail(user.getName());
    	dataDTO.casify();
    	
    	if(!dataDTO.validate())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("Uneti podaci su neispravni!")));
    	
    	if (!u.hasRole("ROLE_DRIVER")) {
    		u.setName(dataDTO.getName());
    		u.setLastName(dataDTO.getLastName());
    		u.setPhone(dataDTO.getPhone());
    		u.setTown(dataDTO.getTown());
    		u.setProfilePhoto(dataDTO.getPhoto());
    		userService.save(u);
    	} else {
    		DriverChangeRequest request = new DriverChangeRequest(dataDTO, u);
    		driverChangeService.createDriverChangeRequest(request);
    	}
    	
    	return new ResponseEntity<>(dataDTO, HttpStatus.OK);
    }
}
