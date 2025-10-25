package com.example.padel.court.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Court {
    private String id;
    private String name;
    private String location;
    private boolean isActive;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int slotMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Court() {}

    public Court(String id, String name, String location, boolean isActive, LocalTime openingTime, LocalTime closingTime, int slotMinutes) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.isActive = isActive;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.slotMinutes = slotMinutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public int getSlotMinutes() {
        return slotMinutes;
    }

    public void setSlotMinutes(int slotMinutes) {
        this.slotMinutes = slotMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }




}
