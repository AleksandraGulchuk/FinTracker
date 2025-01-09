package com.perscolas.fintracker.servise.interfaces;

import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;

/**
 * Service interface for generating the dashboard summary.
 * - Includes a method to generate a DashboardDto containing summary data based on the user's name and selected time duration.
 */
public interface DashboardService {

    DashboardDto generateDashboardSummary(String userName, String timeDuration);

}
