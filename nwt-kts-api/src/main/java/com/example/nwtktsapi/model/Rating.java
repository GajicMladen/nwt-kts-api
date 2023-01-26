package com.example.nwtktsapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false)
	private Long id;
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fare_id", nullable = false)
	private Fare fare;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
	private Client client;
    
	@Column(name = "vehicle_rating")
	private int vehicleRating;
	
	@Column(name = "driver_rating")
	private int driverRating;
	
	@Column(name = "comment")
	private String comment;
	
	public Rating() {};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fare getFare() {
		return fare;
	}

	public void setFare(Fare fare) {
		this.fare = fare;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
	
	
}
