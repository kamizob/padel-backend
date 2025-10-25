package com.example.padel.court.services;

import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtService {
    private final CourtDAO  courtDAO;
    public CourtService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }
    public List<Court> getAllActiveCourts() {
        return courtDAO.findAllActive();
    }
}
