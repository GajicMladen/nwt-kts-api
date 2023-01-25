package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.Rating;

public class RatingDTO {
	
	private int vehicleRating;
	private int driverRating;
	private String comment;
	private String userFullName;
	private String userProfilePhoto;
	
	public RatingDTO() {}
	
	public RatingDTO(Rating r) {
		this.vehicleRating = r.getVehicleRating();
		this.driverRating = r.getDriverRating();
		this.comment = r.getComment();
		this.userFullName = r.getClient().getName() + " " + r.getClient().getLastName();
		this.userProfilePhoto = r.getClient().getProfilePhoto();
	}
	
	public int getVehicleRating() {
		return vehicleRating;
	}
	public void setVehicleRating(int vehicleRating) {
		this.vehicleRating = vehicleRating;
	}
	public int getDriverRating() {
		return driverRating;
	}
	public void setDriverRating(int driverRating) {
		this.driverRating = driverRating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getUserProfilePhoto() {
		return userProfilePhoto;
	}
	public void setUserProfilePhoto(String userProfilePhoto) {
		this.userProfilePhoto = userProfilePhoto;
	}
}	
