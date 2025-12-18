package com.example.padel.system.repository;

public interface SystemConfigDAO {
    boolean isAdminInitialized();

    int markAdminInitialized();
}
