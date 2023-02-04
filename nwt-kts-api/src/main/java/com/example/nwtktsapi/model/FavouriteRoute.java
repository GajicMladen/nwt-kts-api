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
public class FavouriteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Long id;
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fare_id", nullable = false)
	private Fare fare;
	
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
	private Client client;
    
    public FavouriteRoute() {}
    
	public FavouriteRoute(Fare fare, Client client) {
		super();
		this.fare = fare;
		this.client = client;
	}

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
    
    
}
