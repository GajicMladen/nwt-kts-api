package com.example.nwtktsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.dto.RatingDTO;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Rating;
import com.example.nwtktsapi.repository.FareRepository;
import com.example.nwtktsapi.repository.RatingRepository;
import com.example.nwtktsapi.repository.UserRepository;

@Service
public class RatingService {
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FareRepository fareRepository;
	
	public void saveRating(RatingDTO ratingDTO) {
		Rating rating = new Rating();
		rating.setClient((Client) userRepository.findById(ratingDTO.getClientId()).get());
		rating.setComment(ratingDTO.getComment());
		rating.setDriverRating(ratingDTO.getDriverRating());
		rating.setVehicleRating(ratingDTO.getVehicleRating());
		rating.setFare(fareRepository.findById(ratingDTO.getFareId()).get());
		this.ratingRepository.save(rating);
	}
}
