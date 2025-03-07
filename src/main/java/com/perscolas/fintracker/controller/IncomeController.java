package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.servise.interfaces.IncomeService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

/**
 * Controller for handling income-related requests.
 * - Retrieves and displays a summary of incomes for the current user based on a default time duration ("Last 6 months").
 * - Handles the creation, deletion, and updating of incomes via POST requests.
 * - Validates and processes transactions for creating and updating incomes.
 */
@Controller
@RequestMapping("/incomes")
@CrossOrigin
@Validated
@Slf4j
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping()
    public String getSummary(Model model, Principal principal) {
        model.addAttribute("transaction", new TransactionDto());
        model.addAttribute("summary", incomeService.getSummary(principal.getName(), TimeDuration.SIX_MONTHS.stringValue));
        return "incomes";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        log.info("Creation request received for transaction {}", transaction);
        incomeService.createIncome(principal.getName(), transaction);
        log.info("Transaction {} created", transaction);
        return "redirect:/incomes";
    }

    @PostMapping("/delete")
    public String delete(@PathParam("transactionId") UUID transactionId) {
        log.info("Deletion request received for transaction {}", transactionId);
        incomeService.deleteIncome(transactionId);
        log.info("Transaction {} deleted", transactionId);
        return "redirect:/incomes";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        log.info("Update request received for transaction {}", transaction);
        incomeService.updateIncome(principal.getName(), transaction);
        log.info("Transaction {} updated", transaction);
        return "redirect:/incomes";
    }

}
