package com.example.nwtktsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Client extends User{

    @OneToMany(mappedBy = "client")
    private List<Review> reviews;

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<Fare> fares;

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
}
