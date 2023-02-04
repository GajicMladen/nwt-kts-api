package com.example.nwtktsapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.nwtktsapi.dto.AdminRegistrationDTO;
import com.example.nwtktsapi.dto.RegistrationDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.EmailService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;

@RestController
@RequestMapping(value="api/reg", produces=MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService mailService;
	
	private String SUCCESSFUL_ACTIVATION_REDIRECT = "http://localhost:4200/login/success";
	private String ALREADY_ACTIVE_REDIRECT = "http://localhost:4200/login/alreadyactive";
	private String INVALID_ACTIVATION_TRY = "http://localhost:4200/login/invalidactivation";
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO, UriComponentsBuilder ucBuilder){
		// Converts appropriate fields to title case.
		registrationDTO.casify();
		
		Gson gson = new Gson();
		
		if(!registrationDTO.validate())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("Uneti podaci su neispravni!")));
		
		User existingUser = userService.findByEmail(registrationDTO.getEmail());
		
		if (existingUser != null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("E-mail je već upotrebljen!")));
		}
		
		User newUser = userService.save(registrationDTO);
		
		mailService.sendConfirmationEmail(newUser);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@PostMapping("/adminRegister")
	public ResponseEntity<?> adminRegister(@RequestBody AdminRegistrationDTO registrationDTO){
		registrationDTO.casify();
		Gson gson = new Gson();
		
		String err = userService.validateRegistrationDTO(registrationDTO);
		if (err != null) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(gson.toJson(new ErrMsg(err)));
		
		Driver newUser = userService.createDriverFromDto(registrationDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
	
	@GetMapping("/activate/{id}")
	public ResponseEntity<?> activateUser(@PathVariable Long id ){
		
		User user = userService.findById(id).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(INVALID_ACTIVATION_TRY)).build();
		}
		else {
			if (user.isActive())
				return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(ALREADY_ACTIVE_REDIRECT)).build();
			else {
				user.setActive(true);
				userService.save(user);
				return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(SUCCESSFUL_ACTIVATION_REDIRECT)).build();
			}
		}
	}
}
