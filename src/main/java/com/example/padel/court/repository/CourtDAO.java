package com.example.padel.court.repository;

import com.example.padel.court.domain.Court;

import java.time.LocalTime;
import java.util.List;

public interface CourtDAO {
    List<Court> findAllActive();
    int createCourt(Court court);
    int updateCourtActivity(String id, boolean isActive);
    Court findCourtById(String id);
    List<Court> findAll();
    int updateCourtSchedule(String id, LocalTime openTime, LocalTime closeTime, int slotMinutes);
    int countCourts();
    List<Court> findPaged(int page, int size);
    List<Court> findPagedActive(int page, int size);
    int countActiveCourts();


}
