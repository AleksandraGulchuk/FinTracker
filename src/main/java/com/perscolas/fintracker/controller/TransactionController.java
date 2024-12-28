package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.Period;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.servise.TransactionService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/transactions")
@CrossOrigin
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping()
    public String getTransactions(@PathParam("period") String period, Model model, Principal principal) {
        TransactionsSummaryDto summary = transactionService.getTransactionsSummary(principal.getName(), period);
        summary.setActive(Period.periodOfStringValue(period));
        model.addAttribute("dashboard", summary);
        return "transactions";
    }


}
