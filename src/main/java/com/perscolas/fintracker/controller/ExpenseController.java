package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.servise.interfaces.ExpenseService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/expenses")
@CrossOrigin
@Validated
@AllArgsConstructor
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
        expenseService.createExpense(principal.getName(), transaction);
        return "redirect:/expenses";
    }

    @PostMapping("/delete")
    public String delete(@PathParam("transactionId") UUID transactionId) {
        expenseService.deleteExpense(transactionId);
        return "redirect:/expenses";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        expenseService.updateExpense(principal.getName(), transaction);
        return "redirect:/expenses";
    }

}
