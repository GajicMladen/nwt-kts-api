package com.example.nwtktsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nwtktsapi.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
