package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.servise.interfaces.DashboardService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Controller for handling dashboard-related requests.
 * - Handles redirection to the dashboard with a default time duration ("This month").
 * - Retrieves dashboard data based on the selected time duration and adds it to the model for rendering.
 */
@Controller
@RequestMapping("/dashboard")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @RequestMapping("")
    public String home() {
        return "redirect:/dashboard?timeDuration=This+month";
    }

    @GetMapping()
    public String getTransactions(@PathParam("timeDuration") String timeDuration, Model model, Principal principal) {
        log.info("Fetching transactions dashboard for timeDuration: {}", timeDuration);
        DashboardDto dashboard = dashboardService.generateDashboardSummary(principal.getName(), timeDuration);
        dashboard.setActive(TimeDuration.timeDurationOfStringValue(timeDuration));
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }

}
