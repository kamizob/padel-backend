package com.example.padel.court.services;

import com.example.padel.court.domain.Court;
import com.example.padel.court.repository.CourtDAO;
import com.example.padel.exception.custom.CourtNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtService {
    private final CourtDAO courtDAO;

    public CourtService(CourtDAO courtDAO) {
        this.courtDAO = courtDAO;
    }

    public List<Court> getAllActiveCourts() {

        return courtDAO.findAllActive();
    }

    public List<Court> getAllCourts() {

        return courtDAO.findAll();
    }

    public Court getCourtById(String id) {
        Court court = courtDAO.findCourtById(id);
        if (court == null) {
            throw new CourtNotFoundException("Court not found with id: " + id);
        }
        return court;
    }

    public List<Court> getPagedCourts(int page, int size) {

        return courtDAO.findPaged(page, size);
    }

    public int getTotalCourtCount() {

        return courtDAO.countCourts();
    }

    public List<Court> getPagedActiveCourts(int page, int size) {

        return courtDAO.findPagedActive(page, size);
    }

    public int getActiveCourtCount() {

        return courtDAO.countActiveCourts();
    }


}
