package com.example.nwtktsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.repository.FareRepository;
import com.example.nwtktsapi.repository.UserRepository;

@Service
public class FareService {

	private final int PAGE_SIZE = 2;
	
	@Autowired
	FareRepository fareRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<Fare> getFaresByClientPage(Client client, int page, String sort){
		Page<Fare> fares;
		String[] sortArgs = sort.split("-");
		
		String sortType = sortArgs[0];
		String sortDir = sortArgs[1];
		Sort sortObj = Sort.by(sortType);
		
		if(sortDir.equals("asc"))
			sortObj = sortObj.ascending();
		else 
			sortObj = sortObj.descending();
		
		fares = fareRepository.findByClientsContaining(client, PageRequest.of(page, PAGE_SIZE, sortObj));
		
		return fares.toList();
	}
	
	public List<Fare> getFaresByDriverPage(Driver driver, int page, String sort){
		Page<Fare> fares;
		String[] sortArgs = sort.split("-");
		
		String sortType = sortArgs[0];
		String sortDir = sortArgs[1];
		Sort sortObj = Sort.by(sortType);
		
		if(sortDir.equals("asc"))
			sortObj = sortObj.ascending();
		else 
			sortObj = sortObj.descending();
		
		fares = fareRepository.findByDriver(driver, PageRequest.of(page, PAGE_SIZE, sortObj));
		
		return fares.toList();
	}
	
	public List<Fare> getFaresPage(int page, String sort){
		Page<Fare> fares;
		String[] sortArgs = sort.split("-");
		
		String sortType = sortArgs[0];
		String sortDir = sortArgs[1];
		Sort sortObj = Sort.by(sortType);
		
		if(sortDir.equals("asc"))
			sortObj = sortObj.ascending();
		else 
			sortObj = sortObj.descending();
		
		fares = fareRepository.findAll(PageRequest.of(page, PAGE_SIZE, sortObj));
		
		return fares.toList();
	}
	
	public long getFaresCount(Client client) {
		return this.fareRepository.countByClientsContaining(client);
	}
	
	public long getFaresCount(Driver driver) {
		return this.fareRepository.countByDriver(driver);
	}
	
	public long getFaresCount() {
		return this.fareRepository.count();
	}
	
	public Fare getFareById(Long id) {
		return this.fareRepository.findById(id).get();
	}
}
