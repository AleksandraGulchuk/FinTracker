package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.Period;
import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
import com.perscolas.fintracker.servise.IncomeService;
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
        model.addAttribute("transaction", new TransactionSaveDto());
        model.addAttribute("summary", incomeService.getSummary(principal.getName(), Period.SIX_MONTHS.stringValue));
        return "incomes";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TransactionSaveDto transaction, Principal principal) {
        String userName = principal.getName();

        incomeService.createIncome(principal.getName(), transaction);
        return "redirect:/incomes";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        incomeService.deleteIncome(id);
    }

}
