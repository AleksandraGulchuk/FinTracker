package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.dto.transaction.TransactionSaveDto;
import com.perscolas.fintracker.servise.IncomeService;
import com.perscolas.fintracker.servise.UserService;
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
    private final UserService userService;

    @GetMapping()
    public String getSummary(Model model, Principal principal) {
        String userName = principal.getName();
        UUID userId = userService.getUserIdByUserName(userName);
        model.addAttribute("transaction", new TransactionSaveDto());
        model.addAttribute("summary", incomeService.getSummary(userId));
        return "incomes";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute TransactionSaveDto transaction, Principal principal) {
        String userName = principal.getName();
        UUID userId = userService.getUserIdByUserName(userName);
        transaction.setUserId(userId);
        incomeService.createIncome(transaction);
        return "redirect:/incomes";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        incomeService.deleteIncome(id);
    }

}
