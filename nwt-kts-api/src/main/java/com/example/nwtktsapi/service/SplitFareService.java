package com.example.nwtktsapi.service;

import java.util.Optional;

import com.example.nwtktsapi.model.SplitFare;
import com.example.nwtktsapi.repository.SplitFareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitFareService {

    @Autowired
    private SplitFareRepository splitFareRepository;

    public SplitFare crateNewSplitFare() {
        SplitFare splitFare = new SplitFare();
        return save(splitFare);
    }

    public int getValueById(Long id) {
        return splitFareRepository.getNumberOfAgreedById(id);
    }

    public Optional<SplitFare> findById(Long id) {
        return splitFareRepository.findById(id);
    }

    public SplitFare save(SplitFare splitFare) {
        return splitFareRepository.save(splitFare);
    }
}
