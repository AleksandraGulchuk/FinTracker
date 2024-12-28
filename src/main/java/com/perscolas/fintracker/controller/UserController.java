package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.dto.user.UserLoginDto;
import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.servise.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@CrossOrigin
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLoginDto());
        return "login";
    }

    @GetMapping("/create-account")
    public String createAccount(Model model) {
        model.addAttribute("user", new UserSetupDto());
        return "create-account";
    }

    @PostMapping("/create-account")
    public String createAccount(UserSetupDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-account";
        }
        UUID id = service.create(user);
        model.addAttribute("userId", id);
        return "redirect:/dashboard";
    }

}
