package com.example.nwtktsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nwtktsapi.dto.DriverChangeRequestDTO;
import com.example.nwtktsapi.model.DriverChangeRequest;
import com.example.nwtktsapi.service.DriverChangeService;

@RestController
@RequestMapping(value = "/api/admin", produces=MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
	
	private static int PER_PAGE = 3;

	@Autowired
	DriverChangeService driverChangeService;
	
	@GetMapping(value = "/changeRequests/all/{page}")
	public ResponseEntity<DriverChangeRequestDTO> getChangeRequests(@PathVariable int page){
		List<DriverChangeRequest> ret = driverChangeService.getDriverChangesList(page, PER_PAGE);
		long count = driverChangeService.getCount();
		return new ResponseEntity<>(new DriverChangeRequestDTO(count, ret), HttpStatus.OK);
	}
	
	@GetMapping(value = "/changeRequests/deny/{id}")
	public ResponseEntity<?> denyChangeRequest(@PathVariable Long id) {
		driverChangeService.denyChangeRequest(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/changeRequests/approve/{id}")
	public ResponseEntity<?> approveChangeRequest(@PathVariable Long id) {
		driverChangeService.approveChangeRequest(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
