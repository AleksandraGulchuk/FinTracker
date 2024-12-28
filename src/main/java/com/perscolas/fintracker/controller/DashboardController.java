package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.Period;
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
        return "redirect:/dashboard?period=This+month";
    }

    @GetMapping()
    public String getTransactions(@PathParam("period") String period, Model model, Principal principal) {
        DashboardDto dashboard = dashboardService.getDashboard(principal.getName(), period);
        dashboard.setActive(Period.periodOfStringValue(period));
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }

}
