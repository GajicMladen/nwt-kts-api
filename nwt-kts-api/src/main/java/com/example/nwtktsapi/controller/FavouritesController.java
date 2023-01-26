package com.example.nwtktsapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

import com.example.nwtktsapi.dto.FavouriteRouteDTO;
import com.example.nwtktsapi.dto.MarkFavouriteDTO;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.FavouriteRoute;
import com.example.nwtktsapi.service.FareService;
import com.example.nwtktsapi.service.FavouriteRouteService;
import com.example.nwtktsapi.service.UserService;

@RestController
@RequestMapping(value = "/api/favourites", produces=MediaType.APPLICATION_JSON_VALUE)
public class FavouritesController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	FareService fareService;
	
	@Autowired
	FavouriteRouteService favRouteService;
	
	@GetMapping(value = "/getFavouritesForClient")
	public ResponseEntity<?> getFavouritesForClient(Principal user){
		
		Client c = (Client) userService.findByEmail(user.getName());
		if (c == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<FavouriteRoute> faves = c.getFavouriteRoutes();
		List<FavouriteRouteDTO> ret = new ArrayList<FavouriteRouteDTO>();
		
		for (FavouriteRoute fave: faves)
			ret.add(new FavouriteRouteDTO(c.getId(), fave.getFare()));
			
		return new ResponseEntity<List<FavouriteRouteDTO>>(ret, HttpStatus.OK);
	}
	
	@GetMapping(value = "/isRouteFavourite/{fareId}")
	public ResponseEntity<?> isRouteFavourite(Principal user, @PathVariable Long fareId){
		Client c = (Client) userService.findByEmail(user.getName());
		Long favId = favRouteService.favouriteExists(c, fareService.getFareById(fareId));
		boolean isFavourite = favId != -1;
		return new ResponseEntity<>("{\"isFavourite\": "+ isFavourite + ", \"favouriteId\": "+ favId +"}", HttpStatus.OK);
	}
	
	@PostMapping(value = "/addFavouriteForClient")
	public ResponseEntity<?> addFavouriteForClient(Principal user, @RequestBody MarkFavouriteDTO dto) {
		
		Client c = (Client) userService.findByEmail(user.getName());
		if (c == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Fare fare = fareService.getFareById(dto.getFareId());
		if (fare == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		if(favRouteService.favouriteExists(c, fare) != -1)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		FavouriteRoute favRoute = new FavouriteRoute(fare, c);

		Long id = favRouteService.save(favRoute);
		return new ResponseEntity<>("{\"isFavourite\": " + true + ", \"favouriteId\": " + id + "}", HttpStatus.OK);
	}
	
	@PostMapping(value = "/removeFavouriteForClient")
	public ResponseEntity<?> removeFavouriteForClient(Principal user, @RequestBody MarkFavouriteDTO dto) {
		
		Client c = (Client) userService.findByEmail(user.getName());
		if (c == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		FavouriteRoute favRoute = favRouteService.getById(dto.getFavouriteId());
		
		favRouteService.remove(favRoute);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
