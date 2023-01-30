package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Coordinate;
import com.example.nwtktsapi.model.Fare;

public class FavouriteRouteDTO {

	private String routeName;
	private Long clientId;
	private String stops;
	private Long favouriteId;
	
	public FavouriteRouteDTO() {};
	
	public FavouriteRouteDTO(Long clientId, Fare fare, Long favId) {
		this.clientId = clientId;
		this.routeName = fare.getStops().get(0).getName() + " - " + fare.getStops().get(fare.getStops().size() - 1).getName();
		this.favouriteId = favId;
		this.stops = "";
		for (Coordinate cord: fare.getStops()) {
            this.stops = this.stops.concat(cord.getName()+","+
                    String.valueOf(cord.getLatitude())+","+
                    String.valueOf(cord.getLongitude())+";");
        }
	}
	
	public Long getFavouriteId() {
		return favouriteId;
	}

	public void setFavouriteId(Long favouriteId) {
		this.favouriteId = favouriteId;
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

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

}
