package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
import com.perscolas.fintracker.servise.ExpenseService;
import com.perscolas.fintracker.servise.UserService;
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
    private final UserService userService;

    @GetMapping()
    public String getSummary(Model model, Principal principal) {
        UUID userId = userService.getUserIdByUserName(principal.getName());
        model.addAttribute("transaction", new TransactionSaveDto());
        model.addAttribute("summary", expenseService.getSummary(userId));
        return "expenses";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TransactionSaveDto transaction, Principal principal) {
        UUID userId = userService.getUserIdByUserName(principal.getName());
        transaction.setUserId(userId);
        expenseService.createIncome(transaction);
        return "redirect:/expenses";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        expenseService.deleteIncome(id);
    }

}
