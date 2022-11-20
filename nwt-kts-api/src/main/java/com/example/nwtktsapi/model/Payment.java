package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long  paymentID;

    @Column(name = "is_processed")
    private  boolean isProcessed;

    @Column(name = "price" )
    private float price;

    @ManyToOne
    @JoinColumn(name = "fare_id", nullable = false)
    private Fare fare;

    @ManyToOne
    @JoinColumn(name = "client_user_id")
    private Client client;

    public Payment() {
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }
}
