package com.example.nwtktsapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.model.DriverChangeRequest;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.DriverChangeRepository;
import com.example.nwtktsapi.repository.UserRepository;

@Service
public class DriverChangeService {

	@Autowired
	DriverChangeRepository driverChangeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public void createDriverChangeRequest(DriverChangeRequest request) {
		driverChangeRepository.save(request);
	}
	
	public List<DriverChangeRequest> getDriverChangesList(int page, int perPage){
		Pageable paging = PageRequest.of(page, perPage);
		Page<DriverChangeRequest> requests = driverChangeRepository.findByResolvedFalse(paging);
		return requests.toList();
	}
	
	public long getCount() {
		return driverChangeRepository.countByResolvedFalse();
	}
	
	public void approveChangeRequest(Long id) {
		DriverChangeRequest request = driverChangeRepository.findById(id).get();
		
		User u = userRepository.findByEmail(request.getDriver().getEmail());
		u.setName(request.getName());
		u.setLastName(request.getLastName());
		u.setTown(request.getTown());
		u.setPhone(request.getPhone());
		u.setProfilePhoto(request.getProfilePhoto());
		
		request.setResolved(true);
		driverChangeRepository.save(request);
		userRepository.save(u);
	}
	
	public void denyChangeRequest(Long id) {
		DriverChangeRequest request = driverChangeRepository.findById(id).get();
		request.setResolved(true);
		driverChangeRepository.save(request);
	}
}
