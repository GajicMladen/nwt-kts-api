package com.example.nwtktsapi.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client extends User{


    @OneToMany(mappedBy = "client")
    private List<Review> reviews;

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<Fare> fares;
    
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<FavouriteRoute> favouriteRoutes;
    
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Rating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Payment> payments;

    public Client() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Fare> getFares() {
        return fares;
    }

    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

	public List<FavouriteRoute> getFavouriteRoutes() {
		return favouriteRoutes;
	}

	public void setFavouriteRoutes(List<FavouriteRoute> favouriteRoutes) {
		this.favouriteRoutes = favouriteRoutes;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
    
    
}
