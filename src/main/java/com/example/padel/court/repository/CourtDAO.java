package com.example.padel.court.repository;

import com.example.padel.court.domain.Court;

import java.util.List;

public interface CourtDAO {
    List<Court> findAllActive();
    int createCourt(Court court);
    int updateCourtActivity(String id, boolean isActive);
    Court findCourtById(String id);
    List<Court> findAll();
}
