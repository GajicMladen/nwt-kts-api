package com.example.nwtktsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.nwtktsapi.dto.RegistrationDTO;
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
}
