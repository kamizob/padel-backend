package com.example.padel.court.repository;

import com.example.padel.court.domain.Court;

import java.util.List;

public interface CourtDAO {
    List<Court> findAllActive();
}
