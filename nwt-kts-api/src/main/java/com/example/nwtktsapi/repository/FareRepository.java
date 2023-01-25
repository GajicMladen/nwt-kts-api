package com.example.nwtktsapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Fare;

public interface FareRepository extends JpaRepository<Fare, Long>{

	Page<Fare> findByClientsContaining(Client client, Pageable page);
	
	long countByClientsContaining(Client client);
}