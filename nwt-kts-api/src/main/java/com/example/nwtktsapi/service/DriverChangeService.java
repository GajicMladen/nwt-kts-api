package com.example.nwtktsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.DriverChangeRequest;
import com.example.nwtktsapi.repository.DriverChangeRepository;

@Service
public class DriverChangeService {

	@Autowired
	DriverChangeRepository driverChangeRepository;
	
	public void createDriverChangeRequest(DriverChangeRequest request) {
		driverChangeRepository.save(request);
	}
}
