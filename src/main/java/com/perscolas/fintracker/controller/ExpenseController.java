package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.Period;
import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
import com.perscolas.fintracker.servise.ExpenseService;
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
        model.addAttribute("transaction", new TransactionSaveDto());
        model.addAttribute("summary", expenseService.getSummary(principal.getName(), Period.SIX_MONTHS.stringValue));
        return "expenses";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TransactionSaveDto transaction, Principal principal) {
        expenseService.createIncome(principal.getName(), transaction);
        return "redirect:/expenses";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        expenseService.deleteIncome(id);
    }

}
