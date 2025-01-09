package com.perscolas.fintracker.controller;

import com.perscolas.fintracker.model.dto.user.UserLoginDto;
import com.perscolas.fintracker.model.dto.user.UserSetupDto;
import com.perscolas.fintracker.servise.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling user-related actions, such as login and account creation.
 * - Displays the login page and the create account page.
 * - Handles user registration with validation of user input, creating a new user if valid.
 */
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
    public String createAccount(@Valid UserSetupDto user, BindingResult result) {
        log.info("User creation request received for email: {}", user.getEmail());
        if (result.hasErrors()) {
            return "create-account";
        }
        service.create(user);
        log.info("User created successfully: {}", user.getEmail());
        return "redirect:/login";
    }

}
