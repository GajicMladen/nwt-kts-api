package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Fare;

public class FavouriteRouteDTO {

	private String routeName;
	private Long clientId;
	
	public FavouriteRouteDTO() {};
	
	public FavouriteRouteDTO(Long clientId, Fare fare) {
		this.clientId = clientId;
		this.routeName = fare.getStops().get(0).getName() + " - " + fare.getStops().get(fare.getStops().size() - 1).getName();
	}
	
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	
	
}
