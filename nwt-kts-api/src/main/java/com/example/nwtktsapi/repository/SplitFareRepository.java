package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.model.SplitFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SplitFareRepository extends JpaRepository<SplitFare, Long> {
    @Query("select s.numberOfAgreed from SplitFare s where s.id = ?1")
    public Integer getNumberOfAgreedById(Long id);
}
