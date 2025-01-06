package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.dashboard.DashboardDto;
import com.perscolas.fintracker.servise.DashboardService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
@CrossOrigin
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @RequestMapping("")
    public String home() {
        return "redirect:/dashboard?timeDuration=This+month";
    }

    @GetMapping()
    public String getTransactions(@PathParam("timeDuration") String timeDuration, Model model, Principal principal) {
        DashboardDto dashboard = dashboardService.getDashboard(principal.getName(), timeDuration);
        dashboard.setActive(TimeDuration.timeDurationOfStringValue(timeDuration));
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }

}
