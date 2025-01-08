package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;

public interface DashboardService {

    DashboardDto generateDashboardSummary(String userName, String timeDuration);

}
