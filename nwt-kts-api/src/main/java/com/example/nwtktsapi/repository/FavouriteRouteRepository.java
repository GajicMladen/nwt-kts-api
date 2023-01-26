package com.example.nwtktsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.FavouriteRoute;

public interface FavouriteRouteRepository extends JpaRepository<FavouriteRoute, Long>{

	List<FavouriteRoute> findByClient(Client c);
}
