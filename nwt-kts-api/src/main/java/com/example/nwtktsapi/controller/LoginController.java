package com.example.nwtktsapi.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.example.nwtktsapi.service.DriverTimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nwtktsapi.auth.UserTokenState;
import com.example.nwtktsapi.dto.EmailDTO;
import com.example.nwtktsapi.dto.JwtAuthenticationRequest;
import com.example.nwtktsapi.dto.ResetPasswordDTO;
import com.example.nwtktsapi.enums.ResetPasswordStatus;
import com.example.nwtktsapi.model.ResetPassword;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.ResetPasswordService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.EmailService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.example.nwtktsapi.utils.TokenUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService mailService;

	@Autowired
	private DriverTimesheetService driverTimesheetService;
	
	@Autowired
	private ResetPasswordService resetPasswordService;
	
	@PostMapping("/api/login")
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response){
		
		Authentication auth;
		Gson gson = new Gson();
		try {
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));		
		} catch(AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(gson.toJson(new ErrMsg("Neispravan e-mail ili lozinka!")));
		}
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		User user = (User) auth.getPrincipal();
		
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		if(user.isBlocked())
			return new ResponseEntity<>(gson.toJson(new ErrMsg("Korisnik je blokiran!")),HttpStatus.FORBIDDEN);

		if(user.getRoleString().equals("ROLE_DRIVER") && !driverTimesheetService.canLogin(user.getId()))
			return new ResponseEntity<>(gson.toJson(new ErrMsg("Danas ste već radili osam časova!")), HttpStatus.FORBIDDEN);
		
		if(user.isActive())
			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user));
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								 .body(gson.toJson(new ErrMsg("Nalog nije verifikovan!")));
	}
	
	@PostMapping("/api/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody EmailDTO passDTO){
		User user = userService.findByEmail(passDTO.getEmail());
		
		if(user == null || user.isBlocked() || !user.isActive())
			return new ResponseEntity<>(new Gson().toJson(new ErrMsg("Nalog nije validan!")), HttpStatus.BAD_REQUEST);
		
		ResetPassword reset = new ResetPassword(UUID.randomUUID().toString(), passDTO.getEmail(), LocalDateTime.now().plus(Duration.ofMinutes(30)));
		this.mailService.sendResetPasswordEmail(reset);
		this.resetPasswordService.saveRequest(reset);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/api/getTokenStatus/{token}")
	public ResponseEntity<?> getTokenStatus(@PathVariable String token){
		Gson gson = new Gson();
		
		ResetPassword reset = this.resetPasswordService.getByToken(token);
		
		if (reset == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(gson.toJson(new ErrMsg("Neispravan zahtev!")));
		
		if(reset.getExpirationDate().isBefore(LocalDateTime.now()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(gson.toJson(new ErrMsg("Zahtev za promenu lozinke je istekao!")));
		
		if (reset.getStatus().equals(ResetPasswordStatus.RESOLVED))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(gson.toJson(new ErrMsg("Zahtev za promenu lozinke je obrađen!")));
		
		return new ResponseEntity<>("{\"tokenStatus\": " + "\"" + reset.getStatus().toString() + "\"}", HttpStatus.OK);
	}
	
	@PostMapping("/api/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetDTO) {
		Gson gson = new Gson();
		
		if(!resetDTO.validate())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(gson.toJson(new ErrMsg("Uneti podaci su neispravni!")));
		
		ResetPassword reset = this.resetPasswordService.getByToken(resetDTO.getToken());
		
		if(reset.getExpirationDate().isBefore(LocalDateTime.now()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(gson.toJson(new ErrMsg("Zahtev za promenu lozinke je istekao!")));
		
		if (reset.getStatus().equals(ResetPasswordStatus.RESOLVED))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(gson.toJson(new ErrMsg("Zahtev za promenu lozinke je obrađen!")));
		
		User user = userService.findByEmail(reset.getEmail());
		userService.setEncryptedPassword(user, resetDTO.getNewPassword());
		userService.save(user);
		
		reset.setStatus(ResetPasswordStatus.RESOLVED);
		resetPasswordService.saveRequest(reset);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/api/facebookLogin")
	public ResponseEntity<?> loginWithFacebook(@RequestBody EmailDTO email){
		Gson gson = new Gson();
		
		User user = userService.findByEmail(email.getEmail());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(new ErrMsg("E-mail adresa " + email.getEmail() + " nije registrovana. Neophodno je izvršiti registraciju pre prijave.")));
		}
		
		Authentication auth = 
			    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();

		if(user.isBlocked())
			return new ResponseEntity<>(gson.toJson(new ErrMsg("Korisnik je blokiran!")),HttpStatus.FORBIDDEN);
		
		if(user.isActive())
			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user));
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
								 .body(gson.toJson(new ErrMsg("Nalog nije verifikovan!")));
	}
}
