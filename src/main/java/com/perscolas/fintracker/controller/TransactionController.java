package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionsSummaryDto;
import com.perscolas.fintracker.servise.interfaces.TransactionService;
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
 * Controller for handling transaction-related requests.
 * - Retrieves and displays a summary of transactions for the current user based on the selected time duration.
 */
@Controller
@RequestMapping("/transactions")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping()
    public String getTransactions(@PathParam("timeDuration") String timeDuration, Model model, Principal principal) {
        log.info("Fetching transactions for timeDuration: {}", timeDuration);
        TransactionsSummaryDto summary = transactionService.getTransactionsSummary(principal.getName(), timeDuration);
        summary.setActive(TimeDuration.timeDurationOfStringValue(timeDuration));
        model.addAttribute("dashboard", summary);
        return "transactions";
    }

}
