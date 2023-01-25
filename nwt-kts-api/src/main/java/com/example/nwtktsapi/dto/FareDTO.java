package com.example.nwtktsapi.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.Rating;

public class FareDTO {

	private String startTime;
	private String endTime;
	private String startAddress;
	private String endAddress;
	private String price;
	private String driverProfilePhoto;
	private String driverFullName;
	private String vehicle;
	private List<RatingDTO> ratings;
	
	public FareDTO() {}
	
	public FareDTO(Fare fare) {
		this.startTime = fare.getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
		this.endTime = fare.getEndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"));
		this.startAddress = fare.getStartAddress();
		this.endAddress = fare.getEndAddress();
		this.price = String.valueOf(fare.getPrice());
		this.driverProfilePhoto = fare.getDriver().getProfilePhoto();
		this.driverFullName = fare.getDriver().getName() + " " + fare.getDriver().getLastName();
		this.vehicle = fare.getDriver().getVehicle().getName();
		this.ratings = new ArrayList<RatingDTO>();
		
		for (Rating r: fare.getRatings())
			ratings.add(new RatingDTO(r));
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	protected String getDriverProfilePhoto() {
		return driverProfilePhoto;
	}

	protected void setDriverProfilePhoto(String driverProfilePhoto) {
		this.driverProfilePhoto = driverProfilePhoto;
	}

	protected String getDriverFullName() {
		return driverFullName;
	}

	protected void setDriverFullName(String driverFullName) {
		this.driverFullName = driverFullName;
	}

	protected String getVehicle() {
		return vehicle;
	}

	protected void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public List<RatingDTO> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingDTO> ratings) {
		this.ratings = ratings;
	}
	
}