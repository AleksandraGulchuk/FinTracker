package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.TimeDuration;
import com.perscolas.fintracker.model.dto.transaction.TransactionDto;
import com.perscolas.fintracker.servise.interfaces.IncomeService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/incomes")
@CrossOrigin
@Validated
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
        incomeService.createIncome(principal.getName(), transaction);
        return "redirect:/incomes";
    }

    @PostMapping("/delete")
    public String delete(@PathParam("transactionId") UUID transactionId) {
        incomeService.deleteIncome(transactionId);
        return "redirect:/incomes";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute TransactionDto transaction, Principal principal) {
        incomeService.updateIncome(principal.getName(), transaction);
        return "redirect:/incomes";
    }

}
