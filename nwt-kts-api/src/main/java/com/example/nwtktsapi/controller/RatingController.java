package com.example.nwtktsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nwtktsapi.dto.RatingDTO;
import com.example.nwtktsapi.service.RatingService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.ErrMsg;

@RestController
@RequestMapping(value = "/api/rating", produces=MediaType.APPLICATION_JSON_VALUE)
public class RatingController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RatingService ratingService;
	
	@PostMapping(value = "/postRating")
	public ResponseEntity<?> postRating(@RequestBody RatingDTO ratingDTO){
		
		if (!validateRatingDTO(ratingDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		boolean res = ratingService.saveRating(ratingDTO);
		
		if (res)
			return new ResponseEntity<RatingDTO>(ratingDTO, HttpStatus.OK);
		else
			return new ResponseEntity<>(new ErrMsg("Prošao je rok za ocenjivanje!"), HttpStatus.BAD_REQUEST);
	}
	
	private boolean validateRatingDTO(RatingDTO ratingDTO) {
		if(ratingDTO.getDriverRating() > 5 || ratingDTO.getVehicleRating() > 5)
			return false;
		if(ratingDTO.getDriverRating() < 1 || ratingDTO.getVehicleRating() < 1)
			return false;
		return userService.findById(ratingDTO.getClientId()).isPresent();
	}
	
	
}
