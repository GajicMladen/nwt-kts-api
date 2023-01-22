package com.example.nwtktsapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.DriverChangeRequest;

public interface DriverChangeRepository extends JpaRepository<DriverChangeRequest,Long> {

	public Page<DriverChangeRequest> findByResolvedFalse(Pageable page);
	
	public Long countByResolvedFalse();
}
