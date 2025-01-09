package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.servise.interfaces.ExpenseService;
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
 * Controller for handling expense-related requests.
 * - Retrieves and displays a summary of expenses for the current user based on a default time duration ("Last 6 months").
 * - Handles the creation, deletion, and updating of expenses via POST requests.
 * - Validates and processes transactions for creating and updating expenses.
 */
@Controller
@RequestMapping("/expenses")
@CrossOrigin
@Validated
@Slf4j
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping()
    public String getSummary(Model model, Principal principal) {
        model.addAttribute("transaction", new TransactionDto());
        model.addAttribute("summary", expenseService.getSummary(principal.getName(), TimeDuration.SIX_MONTHS.stringValue));
        return "expenses";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        log.info("Creation request received for transaction {}", transaction);
        expenseService.createExpense(principal.getName(), transaction);
        log.info("Transaction {} created", transaction);
        return "redirect:/expenses";
    }

    @PostMapping("/delete")
    public String delete(@PathParam("transactionId") UUID transactionId) {
        log.info("Deletion request received for transaction {}", transactionId);
        expenseService.deleteExpense(transactionId);
        log.info("Transaction {} deleted", transactionId);
        return "redirect:/expenses";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        log.info("Update request received for transaction {}", transaction);
        expenseService.updateExpense(principal.getName(), transaction);
        log.info("Transaction {} updated", transaction);
        return "redirect:/expenses";
    }

}
