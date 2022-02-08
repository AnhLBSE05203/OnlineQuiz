package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    String loginPage(Model model) {
        return "login_page";
    }

    @GetMapping(value = {"", "/home"})
    String homePage(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        return "home_page";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(Model model, Principal principal) {

        return "access_denied_page";
    }
}
