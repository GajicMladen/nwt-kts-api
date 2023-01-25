package com.example.nwtktsapi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nwtktsapi.dto.FareDTO;
import com.example.nwtktsapi.dto.FareHistoryDTO;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.FareService;
import com.example.nwtktsapi.service.UserService;
import com.example.nwtktsapi.utils.ErrMsg;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/fares", produces=MediaType.APPLICATION_JSON_VALUE)
public class FareController {

	
	private final String[] AVAILABLE_SORTS = {"startTime", "startAddress", "endTime", "endAddress", "price"}; 
	private final String[] AVAILABLE_DIRECTIONS = {"asc", "desc"};
	
	@Autowired
	UserService userService;
	
	@Autowired
	FareService fareService;
	
	@GetMapping(value = "/client")
	public ResponseEntity<?> getFaresByClient(@RequestParam("id") Long clientId, @RequestParam("page") int page, @RequestParam("sort") String sort) {
		
		String errorString = validateParams(clientId, page, sort);
		if (errorString != null)
			return new ResponseEntity<>(new Gson().toJson(new ErrMsg(errorString)), HttpStatus.BAD_REQUEST);
		
		Client client = (Client) userService.getUserById(clientId);
		List<Fare> fares = fareService.getFaresByClientPage(client, page, sort);
		List<FareDTO> ret = new ArrayList<FareDTO>();
		for(Fare f: fares) ret.add(new FareDTO(f));
		
		long count = this.fareService.getFaresCount(client);
		
		return new ResponseEntity<>(new Gson().toJson(new FareHistoryDTO(ret, count)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/driver")
	public ResponseEntity<?> getFaresByDriver(@RequestParam("id") Long driverId, @RequestParam("page") int page, @RequestParam("sort") String sort){
		String errorString = validateParams(driverId, page, sort);
		if (errorString != null)
			return new ResponseEntity<>(new Gson().toJson(new ErrMsg(errorString)), HttpStatus.BAD_REQUEST);
		
		Driver driver = (Driver) userService.getUserById(driverId);
		List<Fare> fares = fareService.getFaresByDriverPage(driver, page, sort);
		List<FareDTO> ret = new ArrayList<FareDTO>();
		for(Fare f: fares) ret.add(new FareDTO(f));
		
		long count = this.fareService.getFaresCount(driver);
		
		return new ResponseEntity<>(new Gson().toJson(new FareHistoryDTO(ret, count)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/admin")
	public ResponseEntity<?> getFares(@RequestParam("page") int page, @RequestParam("sort") String sort){
		String errorString = validateParams(null, page, sort);
		if (errorString != null)
			return new ResponseEntity<>(new Gson().toJson(new ErrMsg(errorString)), HttpStatus.BAD_REQUEST);
		
		List<Fare> fares = fareService.getFaresPage(page, sort);
		List<FareDTO> ret = new ArrayList<FareDTO>();
		for(Fare f: fares) ret.add(new FareDTO(f));
		
		long count = this.fareService.getFaresCount();
		
		return new ResponseEntity<>(new Gson().toJson(new FareHistoryDTO(ret, count)), HttpStatus.OK);
	}
	
	private boolean sortStringValid(String sortString) {
		String[] tokens = sortString.split("-");
		if(!Arrays.asList(AVAILABLE_SORTS).contains(tokens[0]))
			return false;
		if(!Arrays.asList(AVAILABLE_DIRECTIONS).contains(tokens[1]))
			return false;
		return true;
	}
	
	private String validateParams(Long id, int page, String sort) {
		if (id != null) {
			User u = userService.getUserById(id);
			
			if (u == null)
				return "Klijent ne postoji!";
		}
		
		if (!sortStringValid(sort))
			return "Nevalidan sort!";
		
		if (page < 0)
			return "Nevalidan page!";
		
		return null;
	}
}
