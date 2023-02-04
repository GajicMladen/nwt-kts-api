package com.example.nwtktsapi.dto;

public class MarkFavouriteDTO {

	private Long favouriteId;
	private Long clientId;
	private Long fareId;
	
	public Long getFavouriteId() {
		return favouriteId;
	}
	public void setFavouriteId(Long favouriteId) {
		this.favouriteId = favouriteId;
	}
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getFareId() {
		return fareId;
	}
	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}
	
	
}
