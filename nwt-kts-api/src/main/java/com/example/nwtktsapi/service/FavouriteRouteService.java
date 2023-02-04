package com.example.nwtktsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.FavouriteRoute;
import com.example.nwtktsapi.repository.FareRepository;
import com.example.nwtktsapi.repository.FavouriteRouteRepository;

@Service
public class FavouriteRouteService {

	@Autowired
	FavouriteRouteRepository favRouteRepository;
	
	@Autowired
	FareRepository fareRepository;
	
	public Long save(FavouriteRoute favRoute) {
		return favRouteRepository.save(favRoute).getId();
	}
	
	public FavouriteRoute getById(Long id) {
		return favRouteRepository.findById(id).get();
	}
	
	public void remove(FavouriteRoute favRoute) {
		favRouteRepository.delete(favRoute);
	}
	
	public Long favouriteExists(Client c, Fare fare) {
		List<FavouriteRoute> faves = favRouteRepository.findByClient(c);
		for(int i = 0; i < faves.size(); i++) {
			FavouriteRoute f = faves.get(i);
			if ((f.getFare().getStartAddress() + " - " + f.getFare().getEndAddress())
					.equals(fare.getStartAddress() + " - " + fare.getEndAddress()))
				return f.getId();

		}
		return -1L;
	}
}
