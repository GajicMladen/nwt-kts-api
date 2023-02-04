package com.example.nwtktsapi.model;

import javax.persistence.*;

@Entity
public class SplitFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number_of_agreed", nullable = false)
    private int numberOfAgreed;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    public SplitFare() {
        this.numberOfAgreed = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfAgreed() {
        return numberOfAgreed;
    }

    public void setNumberOfAgreed(int value) {
        this.numberOfAgreed = value;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
