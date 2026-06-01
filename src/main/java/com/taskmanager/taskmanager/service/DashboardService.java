package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboard(String email);
}